package com.example.testing;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.fragment.app.FragmentManager;

import com.example.testing.activity.MainActivity;
import com.example.testing.adapter.RecyclerAdapter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

public class RecyclerTest {

    @Mock
    private LinkedList<String> mockDataList;

    @Mock
    private FragmentManager mockFragmentManager;

    @Mock
    private MainActivity mainActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mainActivity = new MainActivity();
    }

    @Test
    public void testRecyclerViewData() {
        // Arrange
        boolean isNewAvailable = true; // Replace with your desired value
        mockDataList.add("hello");

        when(mockDataList.size()).thenReturn(5); // Example size
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(
                mainActivity, mockDataList, mockFragmentManager, isNewAvailable);

        // Act
        mainActivity.recyclerViewData();

        // Assert
        // Verify that recyclerAdapter is set on a RecyclerView.
        // You can use yourActivity's RecyclerView reference or mock it if necessary.
        // Verify that notifyDataSetChanged was called on the adapter.
        verify(recyclerAdapter).notifyDataSetChanged();
    }
}
