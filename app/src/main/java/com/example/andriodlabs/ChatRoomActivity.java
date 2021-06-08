package com.example.andriodlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    ArrayList<Message> messageList = new ArrayList<>();
    int positionClicked = 0;
    MyOwnAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        EditText messageEntry = (EditText)findViewById(R.id.message);
        Button sendButton = (Button)findViewById(R.id.sendButton);
        Button receiveButton = (Button)findViewById(R.id.receiveButton);
        ListView messageListView =(ListView)findViewById(R.id.messageListView);

        myAdapter = new MyOwnAdapter();
        messageListView.setAdapter(myAdapter);

        messageListView.setOnItemClickListener(( parent,  view,  position,  id) -> {
            showMessage( position );
        });

        sendButton.setOnClickListener( click ->
        {
            String sendMessage = messageEntry.getText().toString();
            if (sendMessage.isEmpty()) {
                Toast.makeText(this, "Please Enter Text", Toast.LENGTH_LONG).show();
            } else {
                Message newMessage = new Message(sendMessage, true);
                messageList.add(newMessage);
                myAdapter.notifyDataSetChanged();
                messageEntry.setText("");
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
                myAdapter.notifyDataSetChanged();
                messageEntry.setText("");
                Toast.makeText(this, "Message Received!", Toast.LENGTH_LONG).show();
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
                    myAdapter.notifyDataSetChanged(); //there is one less item so update the list
                })
                .setNeutralButton("dismiss", (click, b) -> { })
                .create()
                .show();
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

        public View getView(int position, View old, ViewGroup parent)
        {
            View newView;
            TextView sendMessage;
            TextView receiveMessage;
            Message thisRow = getItem(position);

            if (thisRow.getSentIfTrue()) {
                newView = getLayoutInflater().inflate(R.layout.message_send, parent, false );
                sendMessage = (TextView)newView.findViewById(R.id.message_send);
                sendMessage.setText(thisRow.getMessage());
            } else {
                newView = getLayoutInflater().inflate(R.layout.message_receive, parent, false );
                receiveMessage = (TextView)newView.findViewById(R.id.message_receive);
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

}