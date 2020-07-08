package cn.carbs.wricheditor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.carbs.wricheditor.library.WRichEditor;
import cn.carbs.wricheditor.library.WRichEditorScrollView;
import cn.carbs.wricheditor.library.callbacks.OnRichTypeChangedListener;
import cn.carbs.wricheditor.library.types.RichType;
import cn.carbs.wricheditor.library.utils.TypeUtil;
import cn.carbs.wricheditor.library.views.RichImageView;
import cn.carbs.wricheditor.library.views.RichLineView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnRichTypeChangedListener {

    private WRichEditorScrollView mWRichEditorScrollView;
    private ImageButton mBtnBold;
    private ImageButton mBtnItalic;
    private ImageButton mBtnStrikeThrough;
    private ImageButton mBtnUnderLine;
    private ImageButton mBtnHeadline;
    private ImageButton mBtnQuote;
    private ImageButton mBtnLink;
    private ImageButton mBtnInsertImage;
    private ImageButton mBtnInsertLine;

    private Button mBtnAddEditor;

    private HashMap<RichType, ImageButton> mImageButtonMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWRichEditorScrollView = findViewById(R.id.wrich_editor_view);
        mWRichEditorScrollView.setOnRichTypeChangedListener(this);

        mBtnAddEditor = findViewById(R.id.button_2);
        mBtnAddEditor.setOnClickListener(this);

        mBtnBold = findViewById(R.id.button_bold);
        mBtnBold.setOnClickListener(this);
        mBtnItalic = findViewById(R.id.button_italic);
        mBtnItalic.setOnClickListener(this);
        mBtnStrikeThrough = findViewById(R.id.button_strike_through);
        mBtnStrikeThrough.setOnClickListener(this);
        mBtnUnderLine = findViewById(R.id.button_under_line);
        mBtnUnderLine.setOnClickListener(this);
        mBtnHeadline = findViewById(R.id.button_headline);
        mBtnHeadline.setOnClickListener(this);
        mBtnQuote = findViewById(R.id.button_quote);
        mBtnQuote.setOnClickListener(this);
        mBtnLink = findViewById(R.id.button_link);
        mBtnLink.setOnClickListener(this);
        mBtnInsertLine = findViewById(R.id.button_line);
        mBtnInsertLine.setOnClickListener(this);
        mBtnInsertImage = findViewById(R.id.button_image);
        mBtnInsertImage.setOnClickListener(this);

        mImageButtonMap.put(RichType.BOLD, mBtnBold);
        mImageButtonMap.put(RichType.ITALIC, mBtnItalic);
        mImageButtonMap.put(RichType.STRIKE_THROUGH, mBtnStrikeThrough);
        mImageButtonMap.put(RichType.UNDER_LINE, mBtnUnderLine);
        mImageButtonMap.put(RichType.HEADLINE, mBtnHeadline);
        mImageButtonMap.put(RichType.LINK, mBtnLink);

    }

    private void onBoldClicked() {
        Set<RichType> richTypes = mWRichEditorScrollView.getRichTypes();
        boolean open = TypeUtil.toggleCertainRichType(richTypes, RichType.BOLD);
        mWRichEditorScrollView.updateTextByRichTypeChanged(RichType.BOLD, open, null);
        setButtonTextColor(mBtnBold, open);
    }

    private void onItalicClicked() {
        Set<RichType> richTypes = mWRichEditorScrollView.getRichTypes();
        boolean open = TypeUtil.toggleCertainRichType(richTypes, RichType.ITALIC);
        mWRichEditorScrollView.updateTextByRichTypeChanged(RichType.ITALIC, open, null);
        setButtonTextColor(mBtnItalic, open);
    }

    private void onStrikeThroughClicked() {
        Set<RichType> richTypes = mWRichEditorScrollView.getRichTypes();
        boolean open = TypeUtil.toggleCertainRichType(richTypes, RichType.STRIKE_THROUGH);
        mWRichEditorScrollView.updateTextByRichTypeChanged(RichType.STRIKE_THROUGH, open, null);
        setButtonTextColor(mBtnStrikeThrough, open);
    }

    private void onUnderLineClicked() {
        Set<RichType> richTypes = mWRichEditorScrollView.getRichTypes();
        boolean open = TypeUtil.toggleCertainRichType(richTypes, RichType.UNDER_LINE);
        mWRichEditorScrollView.updateTextByRichTypeChanged(RichType.UNDER_LINE, open, null);
        setButtonTextColor(mBtnUnderLine, open);
    }

    private void onHeadLineClicked() {
        Set<RichType> richTypes = mWRichEditorScrollView.getRichTypes();
        boolean open = TypeUtil.toggleCertainRichType(richTypes, RichType.HEADLINE);
        mWRichEditorScrollView.updateTextByRichTypeChanged(RichType.HEADLINE, open, null);
        setButtonTextColor(mBtnHeadline, open);
    }

    // todo
    private void onInsertQuoteClicked() {
        Log.d("mmm", "onInsertQuoteClicked()");
        Set<RichType> richTypes = mWRichEditorScrollView.getRichTypes();
        boolean open = TypeUtil.toggleCertainRichType(richTypes, RichType.QUOTE);
        mWRichEditorScrollView.updateTextByRichTypeChanged(RichType.QUOTE, open, null);
        setButtonTextColor(mBtnQuote, open);
    }

    private void onInsertLinkClicked() {
        setButtonTextColor(mBtnLink, true);
        WRichEditor wRichEditor = mWRichEditorScrollView.findCurrentOrRecentFocusedRichEditor();
        if (wRichEditor == null) {
            return;
        }
        final int start = wRichEditor.getSelectionStart();
        final int end = wRichEditor.getSelectionEnd();
        if (start == end) {
            Toast.makeText(this, "请先选择要添加链接的文字", Toast.LENGTH_LONG).show();
            setButtonTextColor(mBtnLink, false);
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        View view = getLayoutInflater().inflate(R.layout.dialog_link, null, false);
        final EditText editText = (EditText) view.findViewById(R.id.edit);
        builder.setView(view);
        builder.setTitle("请输入链接");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String link = editText.getText().toString().trim();
                if (TextUtils.isEmpty(link)) {
                    return;
                }
                setButtonTextColor(mBtnLink, false);
                Set<RichType> richTypes = mWRichEditorScrollView.getRichTypes();
                TypeUtil.removeCertainRichType(richTypes, RichType.LINK);
                mWRichEditorScrollView.updateTextByRichTypeChanged(RichType.LINK, true, null);
                setButtonTextColor(mBtnUnderLine, false);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setButtonTextColor(mBtnLink, false);
            }
        });
        builder.create().show();
    }

    private void onInsertLineClicked() {
        RichLineView richLineView = new RichLineView(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        mWRichEditorScrollView.addRichCell(richLineView, lp);
    }

    private void onInsertImageClicked() {
        RichImageView richImageView = new RichImageView(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        mWRichEditorScrollView.addRichCell(richImageView, lp);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        // 顶部功能按钮
        if (id == R.id.button_bold) {
            onBoldClicked();
        } else if (id == R.id.button_italic) {
            onItalicClicked();
        } else if (id == R.id.button_strike_through) {
            onStrikeThroughClicked();
        } else if (id == R.id.button_under_line) {
            onUnderLineClicked();
        } else if (id == R.id.button_headline) {
            onHeadLineClicked();
        } else if (id == R.id.button_link) {
            onInsertLinkClicked();
        } else if (id == R.id.button_line) {
            onInsertLineClicked();
        } else if (id == R.id.button_image) {
            onInsertImageClicked();
        } else if (id == R.id.button_quote) {
            onInsertQuoteClicked();
        } else if (id == R.id.button_2) {
            button2Clicked();
        }
    }

    private void button2Clicked() {
        WRichEditor editText = new WRichEditor(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int size = mWRichEditorScrollView.mRichCellViewList == null ? 0 : mWRichEditorScrollView.mRichCellViewList.size();
        editText.setHint("CELL : " + size);
        if (size % 2 == 0) {
            editText.setBackgroundColor(0x10222222);
        } else {
            editText.setBackgroundColor(0x18222222);
        }
        mWRichEditorScrollView.addRichCell(editText, lp);
    }

    private void setButtonTextColor(ImageButton button, boolean open) {
        if (open) {
            button.setBackgroundColor(0xb06000E0);
        } else {
            button.setBackgroundColor(0xb0000000);
        }
    }

    @Override
    public void onRichTypeChanged(Set<RichType> oldTypes, Set<RichType> newTypes) {
        if (newTypes == null) {
            newTypes = new HashSet<>();
        }
        for (Map.Entry<RichType, ImageButton> entry : mImageButtonMap.entrySet()) {
            if (newTypes.contains(entry.getKey())) {
                setButtonTextColor(entry.getValue(), true);
            } else {
                setButtonTextColor(entry.getValue(), false);
            }
        }
    }
}