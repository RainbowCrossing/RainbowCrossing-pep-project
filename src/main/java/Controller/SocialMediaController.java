package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //app.get("example-endpoint", this::exampleHandler);

            app.post("/register", this::registerAccountHandler);
            app.post("/login", this::accountLoginHandler);
            app.post("/messages", this::createMessageHandler);
            app.get("/messages", this::getAllMessagesHandler);
            app.get("/messages/{message_id}", this::getMessageByIdHandler);
            app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
            app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
            app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserHandler);


        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }

    // Account creation.
    private void registerAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addAccount = accountService.createNewAccountSerivce(account);
        if(addAccount != null){
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(addAccount));
        } else {
            ctx.status(400);
        }

    }

    // Login processing.
    private void accountLoginHandler(Context ctx)throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account accountFound = accountService.findAccountService(account);
        if(accountFound != null){
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(accountFound));
        } else {
            ctx.status(401);
        }
    }

    // Creation of new message.
    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addMessage = messageService.createNewMessageService(message);
        if(addMessage != null){
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(addMessage));
        } else {
            ctx.status(400);
        }
        
    }

    // Getting all messages.
    private void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessageService();
        ctx.json(messages);
        ctx.status(200);

    }

    // Retrieve message by message_id.
    private void getMessageByIdHandler(Context ctx){
        try {
            Message messageFound = messageService.getMessagesById(Integer.parseInt(ctx.pathParam("message_id")));
            if(messageFound != null){
                ctx.json(messageFound);
            } else {
                ctx.json("");
            }
            ctx.status(200);

        } catch (Exception e) {
            // TODO: handle exception
            ctx.status(200);
        }
    }

    // Delete by message_id.
    private void deleteMessageByIdHandler(Context ctx){
        try {
            int id = Integer.parseInt(ctx.pathParam("message_id"));
            Message messageFound = messageService.deleteMessageService(id);
            if(messageFound != null){
                ctx.json(messageFound);
            } else {
                ctx.json("");
            }
            ctx.status(200);
            
        } catch (Exception e) {
            // TODO: handle exception
            ctx.status(200);
        }

    }

    // Update message by message_id.
    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        try {
            int id = Integer.parseInt(ctx.pathParam("message_id"));
            // message.setMessage_id(id);
            Message updateMessage = messageService.updateMessageService(id, message.getMessage_text());

            ctx.json(updateMessage);
            ctx.status(200);
        } catch (Exception e) {
            // TODO: handle exception
            ctx.status(400);
        }

    }

    // Retrieve all messages from specific user.
    private void getAllMessagesByUserHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(messageService.getMessagesByAccountIdService(id));
        ctx.status(200);

    }


}