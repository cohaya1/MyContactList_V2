package com.example.mycontactlistreform;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    boolean isDeleting = false;
    boolean isAddingFavs = false;
    ContactAdapter adapter;

    ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        initListButton();
        initMapButton();
        initSettingsButton();
        initItemClick();
        initAddContactButton();
        initDeleteButton();
        initAddContactButton();
        initAddFavButton();

    }

    @Override
    public void onResume() {
        super.onResume();
        String sortBy = getSharedPreferences("MyContactListPreferences",
                Context.MODE_PRIVATE).getString("sortfield", "contactname");

        String sortOrder = getSharedPreferences("MyContactListPreferences",
                Context.MODE_PRIVATE).getString("sortorder", "ASC");

        ContactDataSource ds = new ContactDataSource(ContactListActivity.this);

        try {
            ds.open();
            contacts = ds.getContacts(sortBy, sortOrder);
            ds.close();
            if (contacts.size() > 0) {
                ListView listView = (ListView) findViewById(R.id.lvContacts);
                adapter = new ContactAdapter(this, contacts);
                listView.setAdapter(adapter);

            }
            else {
                Intent intent = new Intent(ContactListActivity.this, ContactActivity.class);
                startActivity(intent);

            }

        }
        catch (Exception e) {
            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();

        }

    }

    private void initListButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonList);
        ibList.setEnabled(false);

    }

    private void initMapButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonMap);
        ibList.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ContactListActivity.this, ContactMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

        });

    }

    private void initSettingsButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonSettings);
        ibList.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ContactListActivity.this, ContactSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

        });

    }

    private void initItemClick() {
        ListView listView = (ListView) findViewById(R.id.lvContacts);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                Contact selectedContact = contacts.get(position);
                if (isDeleting) {
                    adapter.showDelete(position, itemClicked, ContactListActivity.this, selectedContact);
                    //adapter.showFav(position, itemClicked, ContactListActivity.this, selectedContact);

                }
                else if (isAddingFavs) {
                    adapter.showFav(position, itemClicked, ContactListActivity.this, selectedContact);

                }
                else if (isDeleting && isAddingFavs) {
                    adapter.showDelete(position, itemClicked, ContactListActivity.this, selectedContact);
                    adapter.showFav(position, itemClicked, ContactListActivity.this, selectedContact);

                }
                else {
                    Intent intent = new Intent(ContactListActivity.this,
                            ContactActivity.class);
                    intent.putExtra("contactid", selectedContact.getContactID());
                    startActivity(intent);

                }



            }

        });

    }

    private void initAddContactButton() {
        Button newContact = (Button) findViewById(R.id.buttonAdd);
        newContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactListActivity.this,
                        ContactActivity.class);
                startActivity(intent);

            }

        });

    }

    private void initDeleteButton() {
        final Button deleteButton = (Button) findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDeleting) {
                    deleteButton.setText("Delete");
                    isDeleting = false;
                    adapter.notifyDataSetChanged();

                }
                else {
                    deleteButton.setText("Done Deleting");
                    isDeleting = true;
                    //adapter.showFav(position, itemClicked, ContactListActivity.this, selectedContact);

                }

            }

        });

    }

    private void initAddFavButton() {
        final Button addFavButton = (Button) findViewById(R.id.buttonAddFav);
        addFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAddingFavs) {
                    addFavButton.setText("Add Favs");
                    isAddingFavs = false;

                }
                else {
                    addFavButton.setText("Done Adding Favs");
                    isAddingFavs = true;
                }
            }
        });
    }

}