public interface Consumer extends Node{

    void register(String s);
    void disconnect(Broker b,String s);
    void playData();
}
