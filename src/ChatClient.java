import java.net.URI;
import java.net.URISyntaxException;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

/**
 * Created by ShardulBansal on 16/03/16.
 */
/** This example demonstrates how to create a websocket connection to a server. Only the most important callbacks are overloaded. */
public class ChatClient extends WebSocketClient {

    String messages = "";
    MessagesCallback main;


    public ChatClient( MessagesCallback m, URI serverUri , Draft draft ) {
        super( serverUri, draft );
        main = m;
    }

    public ChatClient( MessagesCallback m, URI serverURI ) {
        super( serverURI );
        main = m;
    }

    private boolean openStatus = false;

    public boolean getOpenStatus() {
        return this.openStatus;
    }
    @Override
    public void onOpen( ServerHandshake handshakedata ) {
        System.out.println( "[Client onOpen] Opened connection" );
        this.openStatus = true;
        // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
    }

    @Override
    public void onMessage( String message ) {
        messages = messages + "\n" + message;
        System.out.println( "received: " + message );
        main.receiveMessagesCallback( message );
    }

    String getMessages() {
        String messagesToReturn = messages;
        messages = "";
        return messagesToReturn;
    }

    @Override
    public void onFragment( Framedata fragment ) {
        System.out.println( "received fragment: " + new String( fragment.getPayloadData().array() ) );
    }

    @Override
    public void onClose( int code, String reason, boolean remote ) {
        // The codecodes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println( "[Client onClose] Connection closed by " + ( remote ? "remote peer" : "us" ) );
        this.openStatus = false;
    }


    @Override
    public void onError( Exception ex ) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
    }

}