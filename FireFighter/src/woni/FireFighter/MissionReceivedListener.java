package woni.FireFighter;

import java.util.List;

import android.content.Context;

public interface MissionReceivedListener 
{
    void onCompleted(Context activity, List<Mission> missions);
    void onFailed(Context activity, Exception exception);
}

