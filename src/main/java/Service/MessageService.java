package Service;

import Model.Message;
// import Util.ConnectionUtil;
// import Model.Account;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;

    // Constructors.
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    // Retrieve all messages.
    public List<Message> getAllMessageService(){
        return messageDAO.getAllMessage();
    }

    // Retrieve messages from specific id.
    public Message getMessagesById(int id){
        return messageDAO.getMessageById(id);
    }

    // Retrieve messages from specific account id.
    public List<Message> getMessagesByAccountIdService(int id){
        return messageDAO.getMessagesByAccountId(id);
    }

    // Insert a new message when all parameters are passed.
    public Message createNewMessageService(Message message){
        if(message.getMessage_text().isBlank() || message.getMessage_text().length() >= 255){
            System.out.println("Message is empty.");
            return null;
        }
        Message newMessage = messageDAO.insertNewMessage(message);
        return newMessage;
    }

    // Updating a message when all parameters are passed.
    public Message updateMessageService(int id, String text){
        Message updateMessage = messageDAO.getMessageById(id);

        if(updateMessage == null){
            return null;
        }
        if(text.isBlank() || text.length() >= 255){
            return null;
        }
        messageDAO.updateMessage(id, text);
        updateMessage = this.getMessagesById(id);
        return updateMessage;
    }

    // Deleting a message by its id.
    public Message deleteMessageService(int id){
        Message message = this.getMessagesById(id);
        messageDAO.deleteMessage(id);
        return message;
    }
    
}
