package com.ucontrol.flowerpot.data;

import java.util.ArrayList;
import java.util.List;

import util.AppContext;

import com.ucontrol.flowerpot.common.LoadFileToLocal;
import com.ucontrol.flowerpot.common.Tools;
import com.ucontrol.flowerpot.po.FlowerState;
import com.ucontrol.flowerpot.po.FriendSay;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UpdateSqliteDataFromServer {
	public UpdateSqliteDataFromServer() {

	}

	public void UpdateFlowerStates() {
		List<FlowerState> flowerStates = new ArrayList<FlowerState>();
		FetchDataFromServer dataFromServer = new FetchDataFromServer();
		flowerStates = dataFromServer.getFlowerStates();
		SqliteControl sqliteControl = new SqliteControl();
		sqliteControl.deleteFlwoerstate();
		LoadFileToLocal loadFileToLocal = new LoadFileToLocal();
		for (FlowerState flowerState : flowerStates) {
			sqliteControl.InsertIntoFlwoerstate(flowerState.getFlowerid(), flowerState.getName(),
					flowerState.getImagepath(), flowerState.getTemperature(),
					flowerState.getHumidity(), flowerState.getPh());
			loadFileToLocal.LoadFile(AppContext.SERVER_IMG_PATH + flowerState.getImagepath(),
					AppContext.LOCAL_IMG_PATH);
		}

		sqliteControl.close();
	}

	public int UpdateFriendSays(String type) {
		List<FriendSay> friendSays = new ArrayList<FriendSay>();
		FetchDataFromServer dataFromServer = new FetchDataFromServer();
		friendSays = dataFromServer.getFriendSays(type, "0");
		SqliteControl sqliteControl = new SqliteControl();
		sqliteControl.DeleteFriendSay();
		LoadFileToLocal loadFileToLocal = new LoadFileToLocal();
		for (FriendSay friendSay : friendSays) {

			sqliteControl.InsertIntoFriendsay(friendSay.getServerid(), friendSay.getName(),
					friendSay.getContent(), friendSay.getIconpath(),
					Tools.listToString(friendSay.getImagepath()), friendSay.getReplynum(),
					friendSay.getTime(), friendSay.getType());
			loadFileToLocal.LoadFile(AppContext.SERVER_IMG_PATH + friendSay.getIconpath(),
					AppContext.LOCAL_IMG_PATH);
		}
	
		// return friendSays.size();
		// friendSays=dataFromServer.getFriendSays("1");
		// for (FriendSay friendSay : friendSays) {
		// sqliteControl.InsertIntoFriendsay(friendSay.getServerid(),friendSay.getName(),friendSay.getContent(),friendSay.getIconpath(),Tools.listToString(friendSay.getImagepath()).toString(),friendSay.getReplynum(),friendSay.getTime(),friendSay.getType());
		// loadFileToLocal.LoadFile(AppContext.SERVER_IMG_PATH+friendSay.getIconpath(),
		// AppContext.LOCAL_IMG_PATH);
		// }
		//
		 sqliteControl.close();
			return 20;
	}

}
