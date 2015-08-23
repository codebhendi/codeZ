
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import network.ChatMessage;
import network.RegisterName;
import network.UpdateNames;
import network.PwdStatus;

// This class is a convenient place to keep things common to both the client and server.
public class Network {

    static public final int port = 54555;

    // This registers objects that are going to be sent over the network.
    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(RegisterName.class);
        kryo.register(String[].class);
        kryo.register(UpdateNames.class);
        kryo.register(ChatMessage.class);
        kryo.register(PwdStatus.class);
    }

}
