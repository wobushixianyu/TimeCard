package com.david.timecard.utils.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.david.timecard.MyApplication;
import com.david.timecard.bean.RecordBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class RecordDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "timecard.db";
    private static final int DB_VERSION = 1;
    private String mTableName; //将登录账号设为表名
    private SQLiteDatabase db;
    private static RecordDBHelper instance;

    public static synchronized RecordDBHelper getInstance() {
        if (instance == null) {
            return instance = new RecordDBHelper(MyApplication.getInstance());
        } else {
            return instance;
        }
    }

    private RecordDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mTableName = "tc_record";
    }

    //初始化，并创建表
    public void init() {
        Timber.i("===RecordDBHelper===>init");
        if (db == null) db = getWritableDatabase();
        String sql = "create table if not exists " + mTableName
                + "(id integer primary key autoincrement,type integer,detail varchar(200),time varchar(30))";
        db.execSQL(sql);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Timber.i("===RecordDBHelper===>onCreate");
        this.db = db;
    }

    //插入数据
    public void insert(ContentValues values) {
        Timber.i("===RecordDBHelper===>insert");
        if (db == null) db = getWritableDatabase();
        db.insert(mTableName, null, values);
    }

    //分页查询10条数据,逆序排列,最后添加的展示在最上面
    public List<RecordBean> rawQuery(int pageIndex) {
        int page = pageIndex - 1;
        if (db == null) db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + mTableName + " order by id desc limit ?,10", new String[]{String.valueOf(page * 10)});
        return filterCursor(cursor);
    }

    //过滤查询结果，剔除超过30天的数据，并返回实体类列表
    private List<RecordBean> filterCursor(Cursor cursor) {
        List<RecordBean> mRecordBeans = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));
                String task = cursor.getString(cursor.getColumnIndex("detail"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                RecordBean mRecord = new RecordBean(id, type, task, time);
                mRecordBeans.add(mRecord);
            } while (cursor.moveToNext());
        }
        return mRecordBeans;
    }

    //删除指定id的数据
    public void delete(int id) {
        Timber.i("===RecordDBHelper===>delete id=%s", id);
        if (db == null) db = getWritableDatabase();
        db.delete(mTableName, "id=?", new String[]{String.valueOf(id)});
    }

    //判断当前数据是否已经超过30天
    @SuppressLint("SimpleDateFormat")
    private boolean hasOverTime(String time) {
        //time的传入格式为yyyy-MM-dd hh:mm:ss
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        try {
            // 获得两个时间的毫秒时间差异
            long diff = System.currentTimeMillis()
                    - sd.parse(time).getTime();
            long day = diff / nd;// 计算差多少天
            // 输出结果
            Timber.i("时间相差：" + day + "天");
            return day > 30;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("update table if exists " + mTableName);
        onCreate(sqLiteDatabase);
    }
}
