package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.*;

public class Main extends TelegramLongPollingBot {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new Main());
    }

    @Override
    public String getBotUsername() {
        return "bot name";
    }

    @Override
    public String getBotToken() {
        return "bot token";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = getChatId(update);

        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            switch (callbackData) {
                case "Settings":
                    sendSettingsMenu(chatId);
                    break;
                case "DecimalPlaces":
                    sendDecimalPlacesMenu(chatId);
                    break;
                case "Banks":
                    sendBanksMenu(chatId);
                    break;
                case "Currencies":
                    sendCurrenciesMenu(chatId);
                    break;
                case "AlertTime":
                    sendAlertTimeMenu(chatId);
                    break;
                default:
            }
        } else {
            // If it's not a callback query, send the initial message with buttons
            SendMessage message = new SendMessage();
            message.setText("Hello, glad to see you. This bot will help you track currency exchange rates.");
            attachButtons(message, Map.of(
                    "Get Info", "GetInfo",
                    "Settings", "Settings"));
            message.setChatId(chatId);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendSettingsMenu(Long chatId) {
        SendMessage settingsMessage = new SendMessage();
        settingsMessage.setText("Settings Menu");
        attachButtons(settingsMessage, Map.of(
                "Number of decimal places", "DecimalPlaces",
                "Banks", "Banks",
                "Currencies", "Currencies",
                "Alert time", "AlertTime",
                "Back", "Settings"));
        settingsMessage.setChatId(chatId);
        try {
            execute(settingsMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendDecimalPlacesMenu(Long chatId) {
        SendMessage decimalPlacesMessage = new SendMessage();
        decimalPlacesMessage.setText("DecimalPlaces");
        attachButtons(decimalPlacesMessage, Map.of(
                "2", "2",
                "3", "3",
                "4", "4",
                "Back", "Settings"));
        decimalPlacesMessage.setChatId(chatId);
        try {
            execute(decimalPlacesMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendBanksMenu(Long chatId) {
        SendMessage banksMessage = new SendMessage();
        banksMessage.setText("Choose bank:");
        attachButtons(banksMessage, Map.of(
                "Monobank", "Monobank",
                "PrivatBank", "PrivatBank",
                "NBU", "NBU",
                "Back", "Settings"));
        banksMessage.setChatId(chatId);
        try {
            execute(banksMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendCurrenciesMenu(Long chatId) {
        SendMessage currenciesMessage = new SendMessage();
        currenciesMessage.setText("Choose currency:");
        attachButtons(currenciesMessage, Map.of(
                "EUR", "EUR",
                "USD", "USD",
                "Back", "Settings"));
        currenciesMessage.setChatId(chatId);
        try {
            execute(currenciesMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendAlertTimeMenu(Long chatId) {
        SendMessage alertTimeMessage = new SendMessage();
        alertTimeMessage.setText("AlertTime:");

        // Creating HashMap to store the buttons
        Map<String, String> buttons = new HashMap<>();
        buttons.put("9", "9");
        buttons.put("10", "10");
        buttons.put("11", "11");
        buttons.put("12", "12");
        buttons.put("13", "13");
        buttons.put("14", "14");
        buttons.put("15", "15");
        buttons.put("16", "16");
        buttons.put("17", "17");
        buttons.put("18", "18");
        buttons.put("19", "19");
        buttons.put("OFF", "OFF");
        buttons.put("Back", "Settings");  // Back button

        // Attaching buttons to the message
        attachButtons(alertTimeMessage, buttons);

        alertTimeMessage.setChatId(chatId);
        try {
            execute(alertTimeMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    public Long getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        }
        return null;
    }

    public void attachButtons(SendMessage message, Map<String, String> buttons) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (String buttonName : buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(buttonName);
            button.setCallbackData(buttonValue);
            keyboard.add(Arrays.asList(button));
        }
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }
}
