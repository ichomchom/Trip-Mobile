package com.csusb.cse455.trip.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.csusb.cse455.trip.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {
    private FirebaseAuth mAuth;

    public ContactUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_us, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        //Get User Inputs
        final String userEmail =  mAuth.getCurrentUser().getEmail();
        final String userName = mAuth.getCurrentUser().getDisplayName();
        final EditText userMsg = (EditText) view.findViewById(R.id.userMsgTxt);
        final Button sendMsgBtn = (Button) view.findViewById(R.id.sendMsgBtn);
        final TextView specificTxt = (TextView) view.findViewById(R.id.specificTxt);
        final EditText emailSubject = (EditText) view.findViewById(R.id.emailSubject);

        //Create subject drop down
        final Spinner subjectsSpinner = (Spinner) view.findViewById(R.id.subjectsSpinner);
        ArrayAdapter<String> adapterSubjects = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.list_contact_us_subjects));
        adapterSubjects.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectsSpinner.setAdapter(adapterSubjects);

        //Create bugs list drop down
        final Spinner bugsSpinner = (Spinner) view.findViewById(R.id.bugsSpinner);
        final ArrayAdapter<String> bugsList = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.list_bugs));
        bugsList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bugsSpinner.setAdapter(bugsList);

        //Set specific bugs list and text hidden
        specificTxt.setVisibility(View.GONE);
        bugsSpinner.setVisibility(View.GONE);
        emailSubject.setVisibility(View.GONE);

        //Set Specific bugs list hidden or show
        subjectsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(subjectsSpinner.getSelectedItemPosition()==1){
                    specificTxt.setVisibility(View.VISIBLE);
                    bugsSpinner.setVisibility(View.VISIBLE);
                }
                else{
                    specificTxt.setVisibility(View.GONE);
                    bugsSpinner.setVisibility(View.GONE);
                }
                if(subjectsSpinner.getSelectedItemPosition()==4){
                    emailSubject.setVisibility(View.VISIBLE);

                }
                else{
                    emailSubject.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //On click Listener for Send Email Button
        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Convert user inputs to String
                String subject = subjectsSpinner.getSelectedItem().toString();
                String bugSubject = bugsSpinner.getSelectedItem().toString();
                String message = userMsg.getText().toString();
                String otherSubject = emailSubject.getText().toString();

                //Check validation for user inputs
                if(subjectsSpinner.getSelectedItemPosition()==0 || subjectsSpinner.getSelectedItemPosition()== 1 && bugsSpinner.getSelectedItemPosition()==0
                        ||TextUtils.isEmpty(message) ) {

                    if (subjectsSpinner.getSelectedItemPosition() == 0) {
                        TextView errorText = (TextView) subjectsSpinner.getSelectedView();
                        errorText.setError("");
                        errorText.setTextColor(Color.RED);
                        errorText.setText("Please Select a Subject");
                        subjectsSpinner.requestFocus();
                    }

                    if (subjectsSpinner.getSelectedItemPosition() == 1 && bugsSpinner.getSelectedItemPosition() == 0) {
                        TextView errorText = (TextView) bugsSpinner.getSelectedView();
                        errorText.setError("");
                        errorText.setTextColor(Color.RED);
                        errorText.setText("Please Select a Subject");
                        bugsSpinner.requestFocus();
                    }

                    if (TextUtils.isEmpty(message)) {
                        userMsg.setError("Please Enter Your Message");
                        userMsg.requestFocus();
                    }
                }
                else {
                    sendEmail(subject, bugSubject, otherSubject, message, userName, userEmail);
                }
            }
        });

    }

    //Send Email
    protected void sendEmail(String subject, String bugSubject, String otherSubject, String message,String userName, String userEmail){
        Intent sendEmail = new Intent(Intent.ACTION_SEND);

        sendEmail.setData(Uri.parse("mailto:"));
        sendEmail.setType("plain/text");
        sendEmail.putExtra(Intent.EXTRA_EMAIL,new String[]{"trip.mobile.contact@gmail.com"});
        sendEmail.putExtra(Intent.EXTRA_SUBJECT,subject+" : "+bugSubject +otherSubject);
        sendEmail.putExtra(Intent.EXTRA_TEXT,
                "name: "+userName+'\n'+"Email: "+userEmail+'\n'+"Message: "+'\n'+message);

        try{
            startActivityForResult(Intent.createChooser(sendEmail, "Send mail..."),1);
            Log.i("Email sent...","");


        }catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(),
                "We have received your email and will be responding to you soon.",
                Toast.LENGTH_LONG).show();

    }

  /*  public void onActivityResult(int requestCode, int resultCode, Intent data){
        if( requestCode == 1 && resultCode == getActivity().RESULT_OK){
            Toast.makeText(getActivity(),
                    "We have received your email and will be responding to you soon.",
                    Toast.LENGTH_LONG).show();
            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
            startActivity(mainIntent);
        }
    }
*/
}
