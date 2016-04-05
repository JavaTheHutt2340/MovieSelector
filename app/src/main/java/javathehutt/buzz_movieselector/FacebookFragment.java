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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import javathehutt.buzz_movieselector.model.DatabaseHelper;
import javathehutt.buzz_movieselector.model.DependencyContainer;
import javathehutt.buzz_movieselector.model.DependencyInjectionContainer;
import javathehutt.buzz_movieselector.model.FacebookUser;
import javathehutt.buzz_movieselector.model.RegUser;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FacebookFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FacebookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FacebookFragment extends Fragment {
    /**
     * the callback manager
     */
    private CallbackManager callbackManager;
    /**
     * the access token
     */
    private static AccessToken at;
    /**
     * the fragment interaction listener
     */
    private OnFragmentInteractionListener mListener;

    /**
     * constructor for facebook fragment
     */
    public FacebookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FacebookFragment.
     */
    public static FacebookFragment newInstance() {
        final FacebookFragment fragment = new FacebookFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final DependencyContainer dc = new DependencyInjectionContainer(getContext());
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getContext());
        callbackManager = dc.getCallbackManagDep();
    }

    /**
     * gets the accessToken
     * @return at the access token
     */
    public static AccessToken getAt() {
        return at;
    }

    /**
     * clears the access token
     */
    public static void clear() {
        at = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_facebook, container, false);
        final LoginButton button = (LoginButton) v.findViewById(R.id.login_button);
        button.setReadPermissions("public_profile", "email", "user_friends");
        button.registerCallback(callbackManager, new CustomCallBack());
        button.setFragment(this);
        return v;
    }

    private class CustomCallBack implements FacebookCallback<LoginResult> {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.e("FACEBOOK", "ONSUCCESS");
            final AccessToken accessToken = loginResult
                    .getAccessToken();
            at = accessToken;
            final GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {
                                final String name = object.getString("name");
                                final String id = object.getString("id");
                                final RegUser u = new FacebookUser(name, id, accessToken);
                                final DatabaseHelper db = new DatabaseHelper(getContext());
                                db.addUser(u);
                                db.handleLogInRequest(name, id);
                                startActivity(new Intent(getContext(), MainMenuActivity.class));
                            } catch (JSONException e) {
                                Log.e("facebook", "error");
                            }

                        }
                    });
            final Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getContext(), "Log In Cancelled!",
                    Toast.LENGTH_SHORT).show();
            Log.i("D", "Facebook login cancelled.");
        }

        @Override
        public void onError(FacebookException error) {
            Toast.makeText(getContext(), "Log In Error!",
                    Toast.LENGTH_SHORT).show();
            Log.e("D", "Facebook login cancelled.");
            Log.e("D", error.toString());
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new IllegalStateException(context.toString()
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
     * "http://developer.android.com/training/basics/fragments/communicating
     * .html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // todo: Update argument type and name

        /**
         * runs upon interaction with a fragment
         * @param uri the uri
         */
        void onFragmentInteraction(Uri uri);
    }
}
