package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {

    ArrayList<Message> messageList = new ArrayList<>();
    int positionClicked = 0;
    MyOwnAdapter myAdapter;
    MyChatOpener chatOpener;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        chatOpener = new MyChatOpener(this);
        database = chatOpener.getWritableDatabase();

        EditText messageEntry = findViewById(R.id.message);
        Button sendButton = findViewById(R.id.sendButton);
        Button receiveButton = findViewById(R.id.receiveButton);
        ListView messageListView = findViewById(R.id.messageListView);

        loadDataFromDatabase();

        myAdapter = new MyOwnAdapter();
        messageListView.setAdapter(myAdapter);
        ContentValues contentValues = new ContentValues();

        messageListView.setOnItemClickListener(( parent,  view,  position,  id) -> showMessage( position ));

        sendButton.setOnClickListener( click -> {
            String sendMessage = messageEntry.getText().toString();
            if (sendMessage.isEmpty()) {
                Toast.makeText(this, "Please Enter Text", Toast.LENGTH_LONG).show();
            } else {
                Message newMessage = new Message(sendMessage, true);
                messageList.add(newMessage);
                contentValues.put(MyChatOpener.COLUMN_MESSAGE, sendMessage);
                contentValues.put(MyChatOpener.COLUMN_SENT_OR_RECEIVED, 1);
                database.insert(MyChatOpener.TABLE_NAME, "null", contentValues);
                messageEntry.setText("");
                myAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Message Sent!", Toast.LENGTH_LONG).show();
            }
        });

        receiveButton.setOnClickListener( click ->
        {
            String receiveMessage = messageEntry.getText().toString();
            if (receiveMessage.isEmpty()) {
                Toast.makeText(this, "Please Enter Text", Toast.LENGTH_LONG).show();
            } else {
                Message newMessage = new Message(receiveMessage, false);
                messageList.add(newMessage);
                contentValues.put(MyChatOpener.COLUMN_MESSAGE, receiveMessage);
                contentValues.put(MyChatOpener.COLUMN_SENT_OR_RECEIVED, 0);
                database.insert(MyChatOpener.TABLE_NAME, "null", contentValues);
                messageEntry.setText("");
                myAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Message Sent!", Toast.LENGTH_LONG).show();
            }
        });


    }


    protected void showMessage(int position)
    {
        Message selectedMessage = messageList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You clicked on item #" + position)
                .setMessage("Would you like to delete the message? \n The Database ID is: " + position)
                .setNegativeButton("Delete", (click, b) -> {
                    messageList.remove(position); //remove the contact from contact list
                    deleteMessage(selectedMessage);
                    myAdapter.notifyDataSetChanged(); //there is one less item so update the list
                })
                .setNeutralButton("dismiss", (click, b) -> { })
                .create()
                .show();
    }

    protected void deleteMessage(Message m) {
        database.delete(MyChatOpener.TABLE_NAME, MyChatOpener.COLUMN_ID + "= ?", new String[] {Long.toString(m.getId())});
    }

    private void printCursor(Cursor cursor) {

        String header = "printCursor(): Column Count: " + cursor.getColumnCount() + " Column Names: " + Arrays.toString(cursor.getColumnNames()) + " Number of Rows: " + cursor.getCount();
        String rows = "\n Rows: ";
        cursor.moveToFirst();
        while(cursor.moveToNext()) {

            int messageIndex = cursor.getColumnIndex(MyChatOpener.COLUMN_MESSAGE);
            int sentOrReceivedIndex = cursor.getColumnIndex(MyChatOpener.COLUMN_SENT_OR_RECEIVED);
            int idColIndex = cursor.getColumnIndex(MyChatOpener.COLUMN_ID);

            String message = cursor.getString(messageIndex);
            long id = cursor.getLong(idColIndex);
            boolean sentOrReceived = cursor.getInt(sentOrReceivedIndex) != 0;

            rows += MyChatOpener.COLUMN_MESSAGE + ": " + message + " " + MyChatOpener.COLUMN_ID + ": " + id + " " + MyChatOpener.COLUMN_SENT_OR_RECEIVED + ": " + sentOrReceived + " \n";
        }

        Log.d("printCursor()",  header+rows);
    }

    //This class needs 4 functions to work properly:
    protected class MyOwnAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return messageList.size();
        }

        public Message getItem(int position){
            return messageList.get(position);
        }

        public View getView(int position, View old, ViewGroup parent) {
            View newView;
            TextView sendMessage;
            TextView receiveMessage;
            Message thisRow = getItem(position);

            if (thisRow.isSent()) {
                newView = getLayoutInflater().inflate(R.layout.message_send, parent, false );
                sendMessage = newView.findViewById(R.id.message_send);
                sendMessage.setText(thisRow.getMessage());
            } else {
                newView = getLayoutInflater().inflate(R.layout.message_receive, parent, false );
                receiveMessage = newView.findViewById(R.id.message_receive);
                receiveMessage.setText(thisRow.getMessage());
            }

            return newView;
        }

        //last week we returned (long) position. Now we return the object's database id that we get from line 71
        public long getItemId(int position)
        {
            return getItem(position).getId();
        }
    }

    private void loadDataFromDatabase() {
        //get a database connection:
        MyChatOpener dbOpener = new MyChatOpener(this);
        database = dbOpener.getWritableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String [] columns = {MyChatOpener.COLUMN_ID, MyChatOpener.COLUMN_MESSAGE, MyChatOpener.COLUMN_SENT_OR_RECEIVED};
        //query all the results from the database:
        Cursor results = database.query(false, MyChatOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int messageIndex = results.getColumnIndex(MyChatOpener.COLUMN_MESSAGE);
        int sentOrReceivedIndex = results.getColumnIndex(MyChatOpener.COLUMN_SENT_OR_RECEIVED);
        int idColIndex = results.getColumnIndex(MyChatOpener.COLUMN_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String message = results.getString(messageIndex);
            long id = results.getLong(idColIndex);
            boolean sentOrReceived = results.getInt(sentOrReceivedIndex) != 0;

            //add the new Contact to the array list:
            messageList.add(new Message(message, id, sentOrReceived));
        }
        printCursor(results);
        //At this point, the contactsList array has loaded every row from the cursor.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        database.close();
    }
}