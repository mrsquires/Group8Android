package library.group8;

import library.group8.constant.SQLCommand;
import library.group8.util.DBOperator;
import library.group8.util.Pair;
import library.group8.view.ChartGenerator;
import library.group8.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class CheckoutActivity extends Activity implements OnClickListener {

	EditText stuIdEdit, bookIdEdit;
	DatePicker datePicker;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout_group8);
		stuIdEdit = (EditText) this.findViewById(R.id.studentID_edittext);
		bookIdEdit = (EditText) this.findViewById(R.id.bookID_edittext);
		datePicker = (DatePicker) this.findViewById(R.id.datePicker1);

		Button backBtn = (Button) this.findViewById(R.id.goBack_btn);
		backBtn.setOnClickListener(this);
		Button checkoutBtn = (Button) this.findViewById(R.id.checkout_btn);
		checkoutBtn.setOnClickListener(this);
		Button returnBtn = (Button) this.findViewById(R.id.return_btn);
		returnBtn.setOnClickListener(this);

		Button summaryBtn = (Button) this.findViewById(R.id.summary_btn);
		summaryBtn.setOnClickListener(this);
	}

	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.checkout_btn) {
			// Check out a book
			DBOperator.getInstance().execSQL(SQLCommand.CHECK_BOOK,
					this.getArgs(true));
			Toast.makeText(getBaseContext(), "Checkout successfully",
					Toast.LENGTH_SHORT).show();
		} else if (id == R.id.return_btn) {
			// Return a book
			DBOperator.getInstance().execSQL(SQLCommand.RETURN_BOOK,
					this.getArgs(false));
			Toast.makeText(getBaseContext(), "Return successfully",
					Toast.LENGTH_SHORT).show();
		} else if (id == R.id.goBack_btn) {
			// Go back to main screen
			Intent intent = new Intent(this, Group8Activity.class);
			this.startActivity(intent);
		} else if (id == R.id.summary_btn) {
			// show summary chart
			Cursor cursor = DBOperator.getInstance().execQuery(
					SQLCommand.CHECKOUT_SUMMARY);
			List<Pair> pairList = new LinkedList<Pair>();
			for (int i = 1; i <= 12; i++) {
				Pair pair = new Pair(i, 0);
				pairList.add(pair);
			}
			while (cursor.moveToNext()) {
				int location = Integer.parseInt(cursor.getString(0));
				pairList.get(location - 1).setNumber(
						Double.parseDouble(cursor.getString(1)));
			}
			Intent intent = ChartGenerator.getBarChart(getBaseContext(),
					"Checkout Summary in 2011", pairList);
			this.startActivity(intent);
		}
	}

	/**
	 * Get input data including studentID, book callnum, date and returned state
	 * 
	 * @param isCheckout
	 * @return
	 */
	private String[] getArgs(boolean isCheckout) {
		String[] args = null;
		if (isCheckout) {
			args = new String[4];
			// stid
			args[0] = stuIdEdit.getText().toString();
			// callnum
			args[1] = bookIdEdit.getText().toString();
			// date
			int year = datePicker.getYear();
			int month = datePicker.getMonth();
			int day = datePicker.getDayOfMonth();
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day);
			// format the date
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			args[2] = dateFormat.format(calendar.getTime());
			// coreturned
			args[3] = "N";
		} else {
			args = new String[3];
			args[0] = "Y";
			args[1] = stuIdEdit.getText().toString();
			args[2] = bookIdEdit.getText().toString();
		}
		return args;
	}
}
