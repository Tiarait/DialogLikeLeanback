package example.leanback.dialog

import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.*
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import example.leanback.Application
import example.leanback.R

class LeanbackDialogFragment: DialogFragment {
    @StyleRes var styleRes = R.style.TransparentDialogAnim
    private var title: String
    private var desc: String
    private var icon: Drawable? = null
    private val buttonList = ArrayList<DialogButton>()
    var dismissWhenClicked = true
    var textIsHtml = true

    constructor(title: String): this(title, "", null)
    constructor(title: String, desc: String): this(title, desc, null)
    constructor(title: String, desc: String, icon: Drawable?): super() {
        this.title = title
        this.desc = desc
        this.icon = icon
    }

    constructor() : this(null, null, null)
    constructor(@StringRes titleRes: Int): this(titleRes, null, null)
    constructor(@StringRes titleRes: Int, @StringRes descRes: Int): this(titleRes, descRes, null)
    constructor(@StringRes titleRes: Int?, @StringRes descRes: Int?, @DrawableRes iconRes: Int?): super() {
        if (titleRes != null) this.title = Application.instance.getString(titleRes)
        else this.title = ""
        if (descRes != null) this.desc = Application.instance.getString(descRes)
        else this.desc = ""
        if (iconRes != null) this.icon = ContextCompat.getDrawable(Application.instance, iconRes)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_leanback_dialog, container)
    }

    override fun getTheme(): Int {
        return styleRes
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        val containerView = view.findViewById<View>(R.id.dialog_container)

        val theme: Resources.Theme = ContextThemeWrapper(Application.instance, styleRes).theme
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.colorBackground, typedValue, true)
        @ColorInt val color: Int = typedValue.data
        containerView.setBackgroundColor(color)

        val titleView = view.findViewById<TextView>(R.id.dialog_title)
        val descView = view.findViewById<TextView>(R.id.dialog_description)
        val iconView = view.findViewById<ImageView>(R.id.dialog_icon)
        val bFirst = view.findViewById<TextView>(R.id.dialog_button_first)
        bFirst?.requestFocus()
        bFirst?.setOnClickListener {
            if (dismissWhenClicked) dismiss()
        }

        titleView?.text = getText(title)
        descView?.text = getText(desc)
        iconView?.setImageDrawable(icon)

        descView?.isVisible = desc.isNotEmpty()
        iconView?.isVisible = icon != null

        view.findViewById<ViewGroup>(R.id.dialog_button_container)?.let {
            val minSize = buttonList.size.coerceAtMost(it.childCount)
            for (x in 0 until minSize) {
                val btn = buttonList[x]
                val btnView = it[x] as TextView
                btnView.text = btn.title
                btnView.isVisible = true
                btnView.setOnClickListener {
                    btn.listener?.onClick(dialog, 0)
                    if (dismissWhenClicked) dismiss()
                }
            }
        }
    }

    private fun getText(title: String): Spanned {
        return if (textIsHtml) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Html.fromHtml(title, Html.FROM_HTML_MODE_COMPACT)
            } else{
                HtmlCompat.fromHtml(title, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
        } else SpannableString(title)
    }

    fun addButton(title: String, listener: DialogInterface.OnClickListener?, ) {
        buttonList.add(DialogButton(title, listener))
    }

    fun addButton(@StringRes textId: Int, listener: DialogInterface.OnClickListener?, ) {
        addButton(Application.instance.getString(textId), listener)
    }

    fun addButton(@StringRes textId: Int) {
        addButton(textId, null)
    }

    fun addButton(title: String) {
        addButton(title, null)
    }

    fun show(manager: FragmentManager) {
        super.show(manager, null)
    }

    class DialogButton(val title: String, val listener: DialogInterface.OnClickListener?)
}