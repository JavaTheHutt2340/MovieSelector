package javathehutt.buzz_movieselector;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.FacebookUser;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FacebookFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FacebookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FacebookFragment extends Fragment {
    private CallbackManager callbackManager;
    private LoginButton button;
    DatabaseHelper helper = new DatabaseHelper(this.getContext());

    private OnFragmentInteractionListener mListener;

    public FacebookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FacebookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FacebookFragment newInstance() {
        FacebookFragment fragment = new FacebookFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getContext());
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_facebook, container, false);
        button = (LoginButton) v.findViewById(R.id.login_button);
        button.setReadPermissions("public_profile", "email", "user_friends");
        button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getContext(), "Logged in successfully!", Toast.LENGTH_SHORT).show();
                Log.i("D", "Facebook login successful.");
                Profile fbProfile = Profile.getCurrentProfile();
                if (fbProfile != null) {
                    Intent i = new Intent();
                    i.setClass(getActivity(), MainMenuActivity.class);
                    FacebookUser u = new FacebookUser(fbProfile);
                    helper.addUser(u);
                    Log.d("frag", u.toString());
                    helper.handleLogInRequest(fbProfile.getId(), fbProfile.getId());
                    startActivity(i);
                    callbackManager.onActivityResult(1, 1, new Intent());
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "Log In Cancelled!", Toast.LENGTH_SHORT).show();
                Log.i("D", "Facebook login cancelled.");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getContext(), "Log In Error!", Toast.LENGTH_SHORT).show();
                Log.e("D", "Facebook login cancelled.");
                Log.e("D", error.toString());
            }
        });
        button.setFragment(this);

        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
