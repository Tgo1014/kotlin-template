package dk.eboks.app.presentation.ui.components.mail.maillist

import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.daimajia.swipe.SwipeLayout
import dk.eboks.app.App
import dk.eboks.app.R
import dk.eboks.app.domain.managers.EboksFormatter
import dk.eboks.app.domain.models.folder.Folder
import dk.eboks.app.domain.models.folder.FolderType
import dk.eboks.app.domain.models.message.Message

class MailMessagesAdapter : RecyclerView.Adapter<MailMessagesAdapter.MessageViewHolder>() {
    enum class MailMessageEvent { OPEN, READ, MOVE }

    private val formatter: EboksFormatter = App.instance().appComponent.eboksFormatter()
    var editMode: Boolean = false
    var messages: MutableList<Message> = arrayListOf()
    var folder: Folder? = null

    var onMessageCheckedChanged: ((Boolean, Message) -> Unit)? = null
    var onActionEvent: ((Message, MailMessageEvent) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
                R.layout.viewholder_message_row,
                parent,
                false
        )
        return MessageViewHolder(v)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder?, position: Int) {
        val last = (position == messages.size)
        holder?.bind(messages[position], last)
    }

    inner class MessageViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
        private val swipeLayout = root as SwipeLayout
        private val contentContainer = root.findViewById<ViewGroup>(R.id.mainContainerView)
        private val markAsReadContainer = root.findViewById<ViewGroup>(R.id.containerMarkAsRead)
        private val moveContainer = root.findViewById<ViewGroup>(R.id.containerMove)
        // Main View
        private val headerTv = contentContainer.findViewById<TextView>(R.id.headerTv)
        private val subHeaderTv = contentContainer.findViewById<TextView>(R.id.subHeaderTv)
        private val dateTv = contentContainer.findViewById<TextView>(R.id.dateTv)
        private val dividerV = contentContainer.findViewById<View>(R.id.dividerV)
        private val checkBox = contentContainer.findViewById<ImageButton>(R.id.checkboxIb)
        private val uploadFl = contentContainer.findViewById<FrameLayout>(R.id.uploadFl)
        private val urgentTv = contentContainer.findViewById<TextView>(R.id.urgentTv)
        private val clipIv = contentContainer.findViewById<ImageView>(R.id.clipIv)
        private val imageIv = contentContainer.findViewById<ImageView>(R.id.imageIv)


        init {
            swipeLayout.showMode = SwipeLayout.ShowMode.PullOut

            swipeLayout.addDrag(SwipeLayout.DragEdge.Left, markAsReadContainer)
            swipeLayout.addDrag(SwipeLayout.DragEdge.Right, moveContainer)
        }

        fun bind(currentItem: Message, last: Boolean) {
            setGeneric(currentItem)

            swipeLayout.isLeftSwipeEnabled = !editMode
            swipeLayout.isRightSwipeEnabled = !editMode

            if (editMode) {
                setSelectable(currentItem, last)
            } else {
                setMessage(currentItem)
            }

            markAsReadContainer.setOnClickListener {
                onActionEvent?.invoke(currentItem, MailMessageEvent.READ)
            }

            moveContainer.setOnClickListener {
                onActionEvent?.invoke(currentItem, MailMessageEvent.MOVE)
            }
        }

        private fun setGeneric(currentItem: Message) {
            if (currentItem.unread) {
                headerTv.setTypeface(null, Typeface.BOLD)
                dateTv?.setTypeface(null, Typeface.BOLD)
                subHeaderTv?.setTypeface(null, Typeface.BOLD)
                dateTv?.setTextColor(ContextCompat.getColor(itemView.context, R.color.darkGreyBlue))
            } else {
                headerTv?.setTypeface(null, Typeface.NORMAL)
                dateTv?.setTypeface(null, Typeface.NORMAL)
                subHeaderTv.setTypeface(null, Typeface.NORMAL)
            }

            headerTv.text = currentItem.sender?.name
            subHeaderTv.text = currentItem.subject
            dateTv.text = formatter.formatDateRelative(currentItem)
            checkBox.isSelected = false


            if (currentItem.status?.text != null) {
                urgentTv?.visibility = View.VISIBLE
                urgentTv?.text = currentItem.status?.text
            } else {
                urgentTv?.visibility = View.GONE
            }

            if (currentItem.numberOfAttachments > 0) {
                clipIv?.visibility = View.VISIBLE
            } else {
                clipIv?.visibility = View.GONE
            }
        }

        private fun setMessage(currentItem: Message) {

            uploadFl.visibility = View.VISIBLE
            checkBox.visibility = View.GONE


            folder?.let {
                if (it.type == FolderType.UPLOADS) {
                    imageIv.setImageResource(R.drawable.ic_menu_uploads)
                    uploadFl.isSelected = false

                } else {
                    Glide.with(itemView.context)
                            .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.icon_48_profile_grey))
                            .load(currentItem.sender?.logo?.url)
                            .into(imageIv)

                    uploadFl.isSelected = currentItem.unread
                }
            }

            val messageListener = View.OnClickListener {
                onActionEvent?.invoke(currentItem, MailMessageEvent.OPEN)
            }

            contentContainer.setOnClickListener(messageListener)
            checkBox.setOnClickListener(messageListener)

        }

        private fun setSelectable(currentItem: Message, last: Boolean) {

            if (last) {
                dividerV.visibility = View.GONE
            }

            uploadFl.visibility = View.GONE
            checkBox.visibility = View.VISIBLE

            val uploadListener = View.OnClickListener {
                if (checkBox.visibility == View.VISIBLE) {

                    val invertedValue = !checkBox.isSelected
                    onMessageCheckedChanged?.invoke(invertedValue, currentItem)
                    checkBox.isSelected = invertedValue

                    if (uploadFl.visibility == View.VISIBLE) {
                        onActionEvent?.invoke(currentItem, MailMessageEvent.OPEN)
                    }

                }
            }

            contentContainer.setOnClickListener(uploadListener)
            checkBox.setOnClickListener(uploadListener)
        }
    }
}