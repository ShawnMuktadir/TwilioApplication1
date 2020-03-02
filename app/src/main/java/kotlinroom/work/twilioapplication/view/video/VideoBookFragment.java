package kotlinroom.work.twilioapplication.view.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import kotlinroom.work.twilioapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoBookFragment extends Fragment {

    @BindView(R.id.fab_add)
    FloatingActionButton fab_add;

    private Context context;

    public VideoBookFragment() {
        // Required empty public constructor
    }

    public static VideoBookFragment newInstance() {
        VideoBookFragment fragment = new VideoBookFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (Objects.requireNonNull(getActivity()).getWindow() != null)
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setListeners();
    }

    private void setListeners() {
        fab_add.setOnClickListener(v -> {
            openVideoActivity();
        });
    }

    private void openVideoActivity() {
        startActivity(new Intent(getActivity(), VideoActivity.class));
    }
}
