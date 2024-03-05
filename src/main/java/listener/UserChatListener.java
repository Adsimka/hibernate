package listener;

import entity.Chat;
import entity.UsersChat;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;

public class UserChatListener {

    @PostPersist
    public void postPersist(UsersChat usersChat) {
        Chat chat = usersChat.getChat();
        chat.setCount(chat.getCount() + 1);
    }

    @PostRemove
    public void postRemove(UsersChat usersChat) {
        Chat chat = usersChat.getChat();
        chat.setCount(chat.getCount() - 1);
    }
}
