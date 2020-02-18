package io.getstream.getstreamlowlevelclientjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;

import io.getstream.chat.android.client.ChatClient;
import io.getstream.chat.android.client.api.models.ChannelWatchRequest;
import io.getstream.chat.android.client.api.models.QueryChannelsRequest;
import io.getstream.chat.android.client.api.models.QuerySort;
import io.getstream.chat.android.client.api.models.QueryUsersRequest;
import io.getstream.chat.android.client.api.models.SearchMessagesRequest;
import io.getstream.chat.android.client.api.models.SendActionRequest;
import io.getstream.chat.android.client.errors.ChatError;
import io.getstream.chat.android.client.events.ConnectedEvent;
import io.getstream.chat.android.client.models.Channel;
import io.getstream.chat.android.client.models.Filters;
import io.getstream.chat.android.client.models.Message;
import io.getstream.chat.android.client.models.User;
import io.getstream.chat.android.client.utils.FilterObject;
import io.getstream.chat.android.client.utils.ProgressCallback;
import io.getstream.chat.android.client.utils.Result;

import static com.google.android.gms.common.util.CollectionUtils.listOf;
import static java.util.Collections.emptyMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = this.getClass().getSimpleName();
    private ChatClient client = ChatClient.Companion.instance();
    private Channel channel;

    private LinearLayout mainContainer;
    private Button channelQueryBtn, channelWatchBtn, channelStopWatchingBtn,
            channelSendMessageBtn, channelShowBtn, channelHideBtn, channelMessageMarkReadBtn,
            channelAcceptInviteBtn, channelRejectInviteBtn, channelUpdateMsgBtn,
            getUsersBtn, userAddMemberBtn, userRemoveMemberBtn, userMuteUserBtn,
            userUnMuteUserBtn, userBanBtn, userUnBanBtn, messageSearchBtn, messageGetBtn,
            messageUpdateBtn, messageSendActionBtn, messageGetRepliesBtn, messageGetReactionsBtn,
            messageDeleteReactionsBtn, messageDeleteBtn, channelRemoveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        HashMap<String, Object> extraData = new HashMap<>();
        extraData.put("name", "Bender");

        User user = new User("bender", extraData);

        client.setUser(user);

        ChatClient.Companion.instance().events().subscribe(FunctionalUtils.fromConsumer(event -> {
            if (event instanceof ConnectedEvent) {
                mainContainer.setVisibility(View.VISIBLE);
            }
            Log.d(TAG, "OnEventReceive: " + event);
        }));

        //client.setGuestUser(user);

        //client.setAnonymousUser();
    }

    private void initViews() {
        mainContainer = findViewById(R.id.testMainContainer);
        channelQueryBtn = findViewById(R.id.channelQueryBtn);
        channelWatchBtn = findViewById(R.id.channelWatchBtn);
        channelStopWatchingBtn = findViewById(R.id.channelStopWatchingBtn);
        channelSendMessageBtn = findViewById(R.id.channelSendMessageBtn);
        channelShowBtn = findViewById(R.id.channelShowBtn);
        channelHideBtn = findViewById(R.id.channelHideBtn);
        channelMessageMarkReadBtn = findViewById(R.id.channelMessageMarkReadBtn);
        channelAcceptInviteBtn = findViewById(R.id.channelAcceptInviteBtn);
        channelRejectInviteBtn = findViewById(R.id.channelRejectInviteBtn);
        channelUpdateMsgBtn = findViewById(R.id.channelUpdateMsgBtn);
        channelRemoveBtn = findViewById(R.id.channelRemoveBtn);

        getUsersBtn = findViewById(R.id.getUsersBtn);
        userAddMemberBtn = findViewById(R.id.userAddMemberBtn);
        userRemoveMemberBtn = findViewById(R.id.userRemoveMemberBtn);
        userMuteUserBtn = findViewById(R.id.userMuteUserBtn);
        userUnMuteUserBtn = findViewById(R.id.userUnMuteUserBtn);
        userBanBtn = findViewById(R.id.userBanBtn);
        userUnBanBtn = findViewById(R.id.userUnBanBtn);
        messageSearchBtn = findViewById(R.id.messageSearchBtn);
        messageGetBtn = findViewById(R.id.messageGetBtn);
        messageUpdateBtn = findViewById(R.id.messageUpdateBtn);
        messageSendActionBtn = findViewById(R.id.messageSendActionBtn);
        messageGetRepliesBtn = findViewById(R.id.messageGetRepliesBtn);
        messageGetReactionsBtn = findViewById(R.id.messageGetReactionsBtn);
        messageDeleteReactionsBtn = findViewById(R.id.messageDeleteReactionsBtn);
        messageDeleteBtn = findViewById(R.id.messageDeleteBtn);

        channelQueryBtn.setOnClickListener(this);
        channelWatchBtn.setOnClickListener(this);
        channelStopWatchingBtn.setOnClickListener(this);
        channelSendMessageBtn.setOnClickListener(this);
        channelShowBtn.setOnClickListener(this);
        channelHideBtn.setOnClickListener(this);
        channelMessageMarkReadBtn.setOnClickListener(this);
        channelAcceptInviteBtn.setOnClickListener(this);
        channelRejectInviteBtn.setOnClickListener(this);
        channelUpdateMsgBtn.setOnClickListener(this);
        channelRemoveBtn.setOnClickListener(this);
        getUsersBtn.setOnClickListener(this);
        userAddMemberBtn.setOnClickListener(this);
        userRemoveMemberBtn.setOnClickListener(this);
        userMuteUserBtn.setOnClickListener(this);
        userUnMuteUserBtn.setOnClickListener(this);
        userBanBtn.setOnClickListener(this);
        userUnBanBtn.setOnClickListener(this);
        messageSearchBtn.setOnClickListener(this);
        messageGetBtn.setOnClickListener(this);
        messageUpdateBtn.setOnClickListener(this);
        messageSendActionBtn.setOnClickListener(this);
        messageGetRepliesBtn.setOnClickListener(this);
        messageGetReactionsBtn.setOnClickListener(this);
        messageDeleteReactionsBtn.setOnClickListener(this);
        messageDeleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.channelQueryBtn:
                queryChannels();
                break;
            case R.id.channelWatchBtn:
                watchChannel();
                break;
            case R.id.channelStopWatchingBtn:
                stopWatching();
                break;
            case R.id.channelSendMessageBtn:
                sendMessageToChannel();
                break;
            case R.id.channelShowBtn:
                showChannel();
                break;
            case R.id.channelHideBtn:
                hideChannel();
                break;
            case R.id.channelMessageMarkReadBtn:
                markAllRead();
                break;
            case R.id.channelAcceptInviteBtn:
                acceptInvite();
                break;
            case R.id.channelRejectInviteBtn:
                rejectInvite();
                break;
            case R.id.channelUpdateMsgBtn:
                updateChannel();
                break;
            case R.id.channelRemoveBtn:
                removeChannel();
                break;
            case R.id.getUsersBtn:
                getUsers();
                break;
            case R.id.userAddMemberBtn:
                addMembers();
                break;
            case R.id.userRemoveMemberBtn:
                removeMembers();
                break;
            case R.id.userMuteUserBtn:
                muteUser();
                break;
            case R.id.userUnMuteUserBtn:
                unMuteUser();
                break;
            case R.id.userBanBtn:
                banUser();
                break;
            case R.id.userUnBanBtn:
                unBanUser();
                break;
            case R.id.messageSearchBtn:
                searchMessages();
                break;
            case R.id.messageGetBtn:
                getMessage();
                break;
            case R.id.messageUpdateBtn:
                updateMessage();
                break;
            case R.id.messageSendActionBtn:
                sendAction();
                break;
            case R.id.messageGetRepliesBtn:
                getReplies();
                break;
            case R.id.messageGetReactionsBtn:
                getReactions();
                break;
            case R.id.messageDeleteReactionsBtn:
                deleteReaction();
                break;
            case R.id.messageDeleteBtn:
                deleteMessage();
                break;
        }
    }

    private void queryChannels() {
        QuerySort sort = new QuerySort().desc("last_message_at");
        FilterObject filter = Filters.INSTANCE.eq("type", "messaging");
        client.queryChannels(
                new QueryChannelsRequest(
                        filter,
                        0,
                        1,
                        sort,
                        0
                )
        ).enqueue(FunctionalUtils.fromConsumer(channelList -> {
            if (channelList.isSuccess()) {
                if (!channelList.data().isEmpty()) {
                    channel = channelList.data().get(0);
                }
            }
        }));
    }

    private void watchChannel() {
        new Thread(() -> {
            Result watchResult = channel.watch(new ChannelWatchRequest()).execute();
            Log.d(TAG, "Watch: " + watchResult);
        }).start();
    }

    private void sendMessageToChannel() {
        Message message = new Message();
        message.setText("My first message");

        client.sendMessage(
                channel.getType(),
                channel.getId(),
                message
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void stopWatching() {
        client.stopWatching(
                channel.getType(),
                channel.getId()
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void showChannel() {
        client.showChannel(
                channel.getType(),
                channel.getId()
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void hideChannel() {
        boolean clearHistory = false;
        client.hideChannel(
                channel.getType(),
                channel.getId(),
                clearHistory
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void markReadMessage() {
        client.markRead(
                channel.getType(),
                channel.getId(),
                "message-id"
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void rejectInvite() {
        client.rejectInvite(
                channel.getType(),
                channel.getId()
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void acceptInvite() {
        client.acceptInvite(
                channel.getType(),
                channel.getId(),
                "hello-accept"
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void updateChannel() {
        HashMap<String, Object> extraData = new HashMap<>();
        Message message = new Message();
        message.setText("Hello");

        client.updateChannel(
                channel.getType(),
                channel.getId(),
                message,
                extraData
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void markAllRead() {
        client.markAllRead().enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void removeChannel() {
        client.deleteChannel(
                channel.getType(),
                channel.getId()
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void getUsers() {
        FilterObject filter = new FilterObject("type", "messaging");
//        FilterObject filter = new FilterObject("banned", false);
//        FilterObject filter = new FilterObject("name", "bender");
//        FilterObject filter = new FilterObject("username", "bender");
//        FilterObject filter = new FilterObject("id", "bender");

        QuerySort sort = new QuerySort().asc("last_active");
        QueryUsersRequest usersQuery = new QueryUsersRequest(filter, 0, 10, sort, false);

        client.getUsers(usersQuery).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void addMembers() {
        client.addMembers(
                channel.getType(),
                channel.getId(),
                listOf("bender")
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void removeMembers() {
        client.removeMembers(
                channel.getType(),
                channel.getId(),
                listOf("bender")
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void muteUser() {
        client.muteUser("bender").enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void unMuteUser() {
        client.unMuteUser("bender").enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void flag() {
        client.flag("bender").enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void banUser() {
        client.banUser(
                "bender",
                channel.getType(),
                channel.getId(),
                "reason",
                10
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void unBanUser() {
        client.unBanUser(
                "bender",
                channel.getType(),
                channel.getId()
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void searchMessages() {
        SearchMessagesRequest searchRequest = new SearchMessagesRequest(
                "Hi",
                0,
                1,
                new FilterObject("type", "messaging")
        );
        client.searchMessages(searchRequest).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void getMessage() {
        client.getMessage("bender-b074ce00-e92a-42ff-b7e4-2137e1d9ea42")
                .enqueue(FunctionalUtils.fromConsumer(result -> {
                    // Check result and show message
                }));
    }

    private void updateMessage() {
        Message msg = new Message();

        msg.setId("bender-b074ce00-e92a-42ff-b7e4-2137e1d9ea42");
        msg.setText("Update text msg");

        client.updateMessage(msg).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void sendAction() {
        //Types "like","love","haha","wow","sad","angry"
        String typeAction = "like";

        SendActionRequest action = new SendActionRequest(
                channel.getId(),
                "bender-b074ce00-e92a-42ff-b7e4-2137e1d9ea42",
                typeAction,
                emptyMap()
        );

        client.sendAction(action).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void getReplies() {
        client.getReplies(
                "bender-b074ce00-e92a-42ff-b7e4-2137e1d9ea42",
                10
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void getReactions() {
        int limit = 10;
        int offset = 0;

        client.getReactions(
                "bender-b074ce00-e92a-42ff-b7e4-2137e1d9ea42",
                limit,
                offset
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void deleteReaction() {
        //Types "like","love","haha","wow","sad","angry"
        String typeAction = "like";

        client.deleteReaction(
                "bender-b074ce00-e92a-42ff-b7e4-2137e1d9ea42",
                typeAction
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void deleteMessage() {
        client.deleteMessage("bender-b074ce00-e92a-42ff-b7e4-2137e1d9ea42")
                .enqueue(FunctionalUtils.fromConsumer(result -> {
                    // Check result and show message
                }));
    }

    private void sendFile(File file) {
        String mimeType = "image";
        client.sendFile(
                channel.getType(),
                channel.getId(),
                file,
                mimeType        //"file"
        );
    }

    private void sendFileProgress(File file) {
        String mimeType = "image";
        client.sendFile(
                channel.getType(),
                channel.getId(),
                file,
                mimeType,     //"file"
                new ProgressCallback() {
                    @Override
                    public void onSuccess(@NotNull String s) {
                        // File load completed
                    }

                    @Override
                    public void onProgress(long l) {
                        // Progress
                    }

                    @Override
                    public void onError(@NotNull ChatError chatError) {
                        // Error on sending file
                    }
                }
        );
    }

    private void deleteFile() {
        client.deleteFile(
                channel.getType(),
                channel.getId(),
                "https://giphy.com/gifs/SJk9xTbxcg0DFDs89d"
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }

    private void deleteImage() {
        client.deleteImage(
                channel.getType(),
                channel.getId(),
                "https://giphy.com/gifs/SJk9xTbxcg0DFDs89d"
        ).enqueue(FunctionalUtils.fromConsumer(result -> {
            // Check result and show message
        }));
    }
}
