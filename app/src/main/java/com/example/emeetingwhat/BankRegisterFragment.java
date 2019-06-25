package com.example.emeetingwhat;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.emeetingwhat.Data.AccountDetailData;
import com.example.emeetingwhat.common.widget.KakaoToast;
import com.kakao.usermgmt.response.model.UserProfile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class BankRegisterFragment extends Fragment {
    private static String IP_ADDRESS = "61.108.100.36";
    private static String TAG = "inserttest";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final UserProfile userProfile = UserProfile.loadFromCache();
    Spinner bankSpinner;
    ArrayAdapter<CharSequence> bankAdapter;
    private String selectedBankName;
    private String inputAccountNumber;
    private EditText accountNumber;
    AccountDetailData accountDetailData;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btn_confirm;
    public BankRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BankDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BankRegisterFragment newInstance(String param1, String param2) {
        BankRegisterFragment fragment = new BankRegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bank_register, container, false);
        // Inflate the layout for this fragment

        bankSpinner = (Spinner)view.findViewById(R.id.sp_bank_list);
        bankAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bankName, android.R.layout.simple_spinner_item);
        bankAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        bankSpinner.setAdapter(bankAdapter);

        bankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?>  parent, View view, int position, long id) {
//                // 확인용
                Toast.makeText(getActivity(),
                        bankAdapter.getItem(position) + "을 선택했습니다.", Toast.LENGTH_LONG).show();

                // 사용자가 선택한 은행명으로 BankName을 세팅한다.
                selectedBankName = bankAdapter.getItem(position).toString();

                accountDetailData = new AccountDetailData();
                accountDetailData.setBankName(selectedBankName);
            }
            public void onNothingSelected(AdapterView  parent) {
                // TODO: validation check (반드시 선택하도록 한다).
            }
        });
        accountNumber = (EditText) view.findViewById(R.id.editText_new_accountNumber) ;


        btn_confirm = (Button) view.findViewById(R.id.button_newaccount_confirm);
        btn_confirm.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : click event
                inputAccountNumber = accountNumber.getText().toString();
                BankRegisterFragment.InsertData task = new BankRegisterFragment.InsertData();
                KakaoToast.makeToast(getActivity(), inputAccountNumber, Toast.LENGTH_SHORT).show();
                task.execute("http://" + IP_ADDRESS + "/insertBank.php"
                        , Long.toString(userProfile.getId()), selectedBankName, inputAccountNumber);
                Fragment fragment = new MyBankAccountFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_fragment_layout, fragment);
                ft.commit();
            }
        });
        return view;
    }
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            // mTextViewResult.setText(result);
            // KakaoToast.makeToast(getActivity(), result, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {
            String userId = (String)params[1];
            String bankName = (String)params[2];
            String acccountNumber = (String)params[3];

            try {

                String serverURL = (String)params[0];
                String postParameters = "userId=" + userId + "&accountNumber=" + acccountNumber + "&bankName=" +bankName;
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }
}
