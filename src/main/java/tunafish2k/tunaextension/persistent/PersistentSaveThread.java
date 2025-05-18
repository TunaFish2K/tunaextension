package tunafish2k.tunaextension.persistent;

public class PersistentSaveThread extends Thread implements Runnable {
    @Override
    public void run() {
        PersistentDatas.save();
    }
}
