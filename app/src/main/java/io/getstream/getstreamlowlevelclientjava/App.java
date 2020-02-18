package io.getstream.getstreamlowlevelclientjava;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import io.getstream.chat.android.client.ChatClient;
import io.getstream.chat.android.client.api.ChatConfig;
import io.getstream.chat.android.client.errors.ChatError;
import io.getstream.chat.android.client.events.ChatEvent;
import io.getstream.chat.android.client.logger.ChatLogger;
import io.getstream.chat.android.client.logger.ChatLoggerHandler;
import io.getstream.chat.android.client.logger.ChatLoggerImpl;
import io.getstream.chat.android.client.logger.ChatLoggerLevel;
import io.getstream.chat.android.client.models.Message;
import io.getstream.chat.android.client.notifications.DeviceRegisteredListener;
import io.getstream.chat.android.client.notifications.NotificationMessageLoadListener;
import io.getstream.chat.android.client.notifications.options.ChatNotificationOptions;
import io.getstream.chat.android.client.notifications.options.NotificationIntentProvider;

public class App extends Application {

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();

        ChatLoggerHandler loggerHandler = new ChatLoggerHandler() {
            @Override
            public void logW(String s, String s1) {
                // display warning logs here
            }

            @Override
            public void logT(Throwable throwable) {
                // display throwable logs here
            }

            @Override
            public void logT(String s, Throwable throwable) {
                // display throwable logs here
            }

            @Override
            public void logI(String s, String s1) {
                // display info logs here
            }

            @Override
            public void logE(String s, String s1) {
                // display error logs here
            }

            @Override
            public void logD(String s, String s1) {
                // display debug logs here
            }
        };

        ChatLogger logger = new ChatLoggerImpl.Builder()
                .level(BuildConfig.DEBUG ? ChatLoggerLevel.ALL : ChatLoggerLevel.NOTHING)
                .handler(loggerHandler)
                .build();

        //Prod base url: chat-us-east-1.stream-io-api.com
        ChatConfig config = new ChatConfig.Builder()
                .apiKey("qk4nn7rpcn75")
                .baseUrl("chat-us-east-staging.stream-io-api.com")
                .cdnUrl("chat-us-east-staging.stream-io-api.com")
                .baseTimeout(10000)
                .cdnTimeout(10000)
                .token("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiYmVuZGVyIn0.3KYJIoYvSPgTURznP8nWvsA2Yj2-vLqrm-ubqAeOlcQ")
                .build();

        /*ChatNotificationConfig notificationConfig = new ChatNotificationConfig.Builder()
                .options(provideNotificationOptions())
                .registerListener(provideDeviceRegisteredListener())
                .messageLoadListener(provideNotificationMessageLoadListener())
                .build();*/

        ChatClient.Builder clientBuilder = new ChatClient.Builder()
                .config(config)
                .logger(logger);
                //.notification(notificationConfig);

        ChatClient.Companion.init(clientBuilder);
    }

    private ChatNotificationOptions provideNotificationOptions() {

        ChatNotificationOptions options = new ChatNotificationOptions();
        options.setNotificationIntentProvider(
                new NotificationIntentProvider() {
                    @Override
                    public PendingIntent getIntentForWebSocketEvent(Context context, ChatEvent chatEvent) {
                        Intent intent = new Intent(context, MainActivity.class);
                        return PendingIntent.getActivity(
                                context, 999,
                                intent, PendingIntent.FLAG_UPDATE_CURRENT
                        );
                    }

                    @Override
                    public PendingIntent getIntentForFirebaseMessage(Context context, RemoteMessage remoteMessage) {
                        //remoteMessage.getData();
                        Intent intent = new Intent(context, MainActivity.class);

                        return PendingIntent.getActivity(
                                context, 999,
                                intent, PendingIntent.FLAG_UPDATE_CURRENT
                        );
                    }
                }
        );
        return options;
    }

    private DeviceRegisteredListener provideDeviceRegisteredListener() {
        DeviceRegisteredListener registerListener = new DeviceRegisteredListener() {

            @Override
            public void onDeviceRegisteredSuccess() {
                Log.i(TAG, "Device registered successfully");
            }

            @Override
            public void onDeviceRegisteredError(ChatError chatError) {
                Log.e(TAG, "onDeviceRegisteredError: ${error.message}");
            }
        };
        return registerListener;
    }

    private NotificationMessageLoadListener provideNotificationMessageLoadListener() {
        NotificationMessageLoadListener messageLoadListener = new NotificationMessageLoadListener() {

            @Override
            public void onLoadMessageSuccess(Message message) {
                Log.d(TAG, "On message loaded. Message:$message");
            }

            @Override
            public void onLoadMessageFail(String s) {
                Log.d(TAG, "Message from notification load fails. MessageId:$messageId");
            }
        };
        return messageLoadListener;
    }
}
