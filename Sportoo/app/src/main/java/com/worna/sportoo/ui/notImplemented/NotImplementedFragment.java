package com.worna.sportoo.ui.notImplemented;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.worna.sportoo.R;

public class NotImplementedFragment extends Fragment {

    public NotImplementedFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.not_implemented_fragment, container, false);

        return root;
    }
}
