package tistory.zxcv5500.typingandroidprogrammingconquest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import tistory.zxcv5500.typingandroidprogrammingconquest.c04_view.TextViewTest;

public class AndExam extends Activity {
	static final int SETTING_ACTIVITY = 1;
	// 예제가 있는 시작 챕터. 스피너의 첨자에 이 값을 더해야 창 번호가 된다.
	static final int START_CHAPTER = 4;

	class Example {
		Class<?> cls;
		String Name;
		String Desc;

		Example(Class<?> acls, String aDesc) {
			cls = acls;
			Name = cls.getSimpleName();
			Desc = aDesc;
		}
	}

	ArrayList<Example> arExample = new ArrayList<Example>();

	// 요청한 장의 에제들을 배열에 채운다.
	void fillExample(int chapter) {
		arExample.clear();;

		switch (chapter) {
			case 4:     // 뷰
				arExample.add(new Example(TextViewTest.class, "텍스트 뷰 위젯 소개, 3개의 문자열 출력"));
				break;

			/*
			case :

				break;
			//*/
		}
	}

	// 예제는 4장부터 제공된다. 4(START_CHAPTER) 장이 첨자 0번이다.
	String[] arChapter = {
			"4장 뷰",
			"5장 레이아웃",
			"6장 레이아웃 관리",
//			"7장 출력",
//			"8장 입력",
//			"9장 메뉴"

	};

	ArrayAdapter<CharSequence> mAdapter;
	ListView mExamList;
	Spinner mSpinner;
	boolean mInitSelection = true;
	int mFontSize;
	int mBackType;
	boolean mDescSide;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.andexam);

		mExamList = (ListView) findViewById(R.id.examlist);
		mSpinner = (Spinner) findViewById(R.id.spinnerchapter);
		mSpinner.setPrompt("장을 선택하세요.");

		mAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, arChapter);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(mAdapter);

		mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// 최초 전개시에도 Selected가 호출되는데 이때는 프레퍼런스에서 최후 장을 찾아 로드한다.
				// 이후부터는 사용자가 선택한 장을 로드한다.
				if (mInitSelection) {
					mInitSelection = false;
					SharedPreferences pref = getSharedPreferences("AndExam", 0);
					int lastchapter = pref.getInt("LastChapter", START_CHAPTER);
					mSpinner.setSelection(lastchapter - START_CHAPTER);
					changeChapter(lastchapter);
				} else {
					//장을 변경할 때 마다 프레퍼런스에 기록한다.
					int chapter = position + START_CHAPTER;


				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	// 장을 변경한다. 인수로 전달되는 chapter는 첨자가 아니라 장 번호이다.
	private void changeChapter(int chapter) {
		fillExample(chapter);

	}


	class ExamListAdapter extends BaseAdapter {
		Context maincon;
		LayoutInflater Inflater;

		public ExamListAdapter(Context context) {
			maincon = context;
			Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return arExample.size();
		}

		@Override
		public String getItem(int position) {
			return arExample.get(position).Name;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = Inflater.inflate(R.layout.andexamlist, parent, false);
			}

			LinearLayout examlayout = (LinearLayout) convertView.findViewById(R.id.examlayout);
			TextView txt1 = (TextView) convertView.findViewById(R.id.text1);
			TextView txt2 = (TextView) convertView.findViewById(R.id.text2);

			if (mDescSide) {
				examlayout.setOrientation(LinearLayout.HORIZONTAL);
			}

			if (mBackType != 0) {
				examlayout.setBackgroundResource(R.drawable.exambackdark);
				txt1.setTextColor(Color.WHITE);
				txt2.setTextColor(Color.LTGRAY);
			}

			switch (mFontSize) {
				case 0:
					txt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
					txt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
					break;
				case 1:
					txt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
					txt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
					break;
				case 2:
					txt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
					txt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
					break;
			}

			txt1.setText(arExample.get(position).Name);
			txt2.setText(arExample.get(position).Desc);

			return convertView;
		}
	}

}
