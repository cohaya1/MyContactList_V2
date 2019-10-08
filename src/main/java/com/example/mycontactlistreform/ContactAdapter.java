package com.example.mycontactlistreform;

import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Toast;



public class ContactAdapter extends ArrayAdapter<Contact> {

    private ArrayList<Contact> items;
    private Context adapterContext;

    public ContactAdapter(Context context, ArrayList<Contact> items) {
        super(context, R.layout.list_item, items);
        adapterContext = context;
        this.items = items;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        try {
            Contact contact = items.get(position);

            if (v == null) {
                LayoutInflater vi = (LayoutInflater)
                        adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_item, null);
            }

            TextView contactName = (TextView) v.findViewById(R.id.textContactName);
            if (position % 2 == 0) {
                contactName.setTextColor(parent.getResources().getColor(R.color.system_red));

            }
            else {
                contactName.setTextColor(parent.getResources().getColor(R.color.system_blue));

            }
            TextView contactHomeNumber = (TextView) v.findViewById(R.id.textPhoneNumber);
            TextView contactCellNumber = (TextView) v.findViewById(R.id.textCellNumber);
            TextView contactAddress = (TextView) v.findViewById(R.id.textStreetAddress);
            TextView contactCity = (TextView) v.findViewById(R.id.textCity);
            TextView contactState = (TextView) v.findViewById(R.id.textState);
            TextView contactZip = (TextView) v.findViewById(R.id.textZip);
            ImageButton ib = (ImageButton) v.findViewById(R.id.buttonFavContact);
            Button b = (Button) v.findViewById(R.id.buttonDeleteContact);
            contactName.setText(contact.getContactName());
            contactHomeNumber.setText("Home: " + contact.getPhoneNumber());
            contactCellNumber.setText("Cell: " + contact.getCellNumber());
            contactAddress.setText("Address: " + contact.getStreetAddress());
            contactCity.setText(contact.getCity());
            contactState.setText(contact.getState());
            contactZip.setText(contact.getZipCode());
//Create ImageView for the best friend icon
            ImageView bestFriendLabel  = (ImageView)v.findViewById(R.id.buttonFavContact);

            //Set best friend star icon to invisible
            bestFriendLabel.setVisibility(View.INVISIBLE);

        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();

        }
        return v;

    }



    public void showDelete(final int position, final View convertView, final Context context,
                           final Contact contact) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.buttonDeleteContact);
        if (b.getVisibility() == View.INVISIBLE) {
            b.setVisibility(View.VISIBLE);
            //showFav(position, convertView, adapterContext, contact);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideDelete(position, convertView, context);
                    items.remove(contact);
                    deleteOption(contact.getContactID(), context);

                }

            });

        }
        else {
            hideDelete(position, convertView,context);

        }

    }

    private void deleteOption(int contactToDelete, Context context) {
        ContactDataSource db = new ContactDataSource(context);
        try {
            db.open();
            db.deleteContact(contactToDelete);
            db.close();

        }
        catch (Exception e) {
            Toast.makeText(adapterContext, "Delete Contact Failed", Toast.LENGTH_LONG).show();

        }
        this.notifyDataSetChanged();

    }

    public void hideDelete(int position, View convertView, Context context) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.buttonDeleteContact);
        b.setVisibility(View.INVISIBLE);
        b.setOnClickListener(null);

    }

}
