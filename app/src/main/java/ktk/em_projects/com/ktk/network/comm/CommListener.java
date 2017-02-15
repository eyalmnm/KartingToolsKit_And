package ktk.em_projects.com.ktk.network.comm;

public interface CommListener {
    public void newDataArrived(String newData);

    public void exceptionThrown(Throwable throwable);
}
