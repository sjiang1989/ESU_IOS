// Copyright 2014 Ellucian Company L.P and its affiliates.

package com.ellucian.mobile.android.registration;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ellucian.elluciango.R;
import com.ellucian.mobile.android.app.EllucianFragment;
import com.ellucian.mobile.android.client.registration.Message;
import com.ellucian.mobile.android.client.registration.RegisterSection;
import com.ellucian.mobile.android.client.registration.RegistrationResponse;

public class RegistrationResultsFragment extends EllucianFragment {
	
	protected View rootView;
	protected RegistrationResponse response;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_registration_results, container, false);
		response = getArguments().getParcelable("RegistrationResponse");
		
		if (response.messages != null && response.messages.length > 0) {
			
			LinearLayout generalLayout = (LinearLayout) rootView.findViewById(R.id.general_layout);
			
			for (Message message : response.messages) {
				final TextView messageView = (TextView) inflater.inflate(
						R.layout.registration_message_row, generalLayout, false);
				messageView.setText(message.message);
				// text color
				int statusColor = getResources().getColor(R.color.status_important_text_color);
				messageView.setTextColor(statusColor);
				generalLayout.addView(messageView);
			}
			
		} else {
			rootView.findViewById(R.id.general_header).setVisibility(View.GONE);
			rootView.findViewById(R.id.general_sub_header).setVisibility(View.GONE);
		}
		
		if (response.successes != null && response.successes.length > 0) {
			
			LinearLayout successLayout = (LinearLayout) rootView.findViewById(R.id.success_layout);
			for (RegisterSection section: response.successes) {
				final LinearLayout sectionRow = (LinearLayout) inflater.inflate(
						R.layout.registration_section_row, successLayout, false);
				
				String courseName = section.courseName;
				if (!TextUtils.isEmpty(section.courseSectionNumber)) {
					courseName = getString(R.string.default_course_section_format,
										section.courseName,
										section.courseSectionNumber);
				}
				((TextView)sectionRow.findViewById(R.id.course_name)).setText(courseName);
				((TextView)sectionRow.findViewById(R.id.course_title)).setText(section.courseTitle);
				
				if (section.messages != null) {
					for (Message message : section.messages) {
						final TextView messageView = (TextView) inflater.inflate(
								R.layout.registration_message_row, sectionRow, false);
						messageView.setText(message.message);
						// text color
						int statusColor = getResources().getColor(R.color.status_success_text_color);
						messageView.setTextColor(statusColor);
						
						sectionRow.addView(messageView);
					}
				}
				
				successLayout.addView(sectionRow);
			}

		} else {
			rootView.findViewById(R.id.success_header).setVisibility(View.GONE);
			rootView.findViewById(R.id.success_sub_header).setVisibility(View.GONE);
		}
		
		if (response.failures != null && response.failures.length > 0) {
			
			LinearLayout failureLayout = (LinearLayout) rootView.findViewById(R.id.failure_layout);
			for (RegisterSection section: response.failures) {
				final LinearLayout sectionRow = (LinearLayout) inflater.inflate(
						R.layout.registration_section_row, failureLayout, false);
				
				String courseName = section.courseName;
				if (!TextUtils.isEmpty(section.courseSectionNumber)) {
					courseName = getString(R.string.default_course_section_format,
										section.courseName,
										section.courseSectionNumber);
				}
				((TextView)sectionRow.findViewById(R.id.course_name)).setText(courseName);
				((TextView)sectionRow.findViewById(R.id.course_title)).setText(section.courseTitle);
				if (section.messages != null) {
					for (Message message : section.messages) {
						final TextView messageView = (TextView) inflater.inflate(
								R.layout.registration_message_row, sectionRow, false);
						messageView.setText(message.message);
						// text color
						int statusColor = getResources().getColor(R.color.status_error_text_color);
						messageView.setTextColor(statusColor);
						sectionRow.addView(messageView);
					}
				}
				
				failureLayout.addView(sectionRow);
				
			}

		} else {
			rootView.findViewById(R.id.failure_header).setVisibility(View.GONE);
			rootView.findViewById(R.id.failure_sub_header).setVisibility(View.GONE);
		}
		
		return rootView;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		sendView("Registration Results", getEllucianActivity().moduleName);
	}

}
