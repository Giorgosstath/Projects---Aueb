import java.util.*;

public interface Node {
    //methods
    public List<BrokerInfo> getBrokersInfo();

    public void connect(int port);

    public void discconect();
}

