package com.nurbk.ps.demochat.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import io.socket.client.Socket
import umn.ac.id.myapplication.databinding.FragmentMainBinding
import umn.ac.id.myapplication.ui.api.SocketManager
import umn.ac.id.myapplication.ui.chat.model.User
import umn.ac.id.myapplication.ui.chat.other.ConfigUser
import umn.ac.id.myapplication.ui.chat.other.DATA_USER_NAME
import umn.ac.id.myapplication.ui.chat.other.IS_CONNECTING
import umn.ac.id.myapplication.ui.chat.ui.fragment.ListUserFragment
import umn.ac.id.myapplication.ui.chat.adapter.ViewPagerAdapter


class MainFragment : Fragment() {

    private lateinit var mBinding: FragmentMainBinding
    private val userString by lazy {
        ConfigUser.getInstance(requireContext())!!.getPreferences()!!.getString(DATA_USER_NAME, "")
    }
    private lateinit var user: User
    private var mSocket: Socket? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMainBinding.inflate(layoutInflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }

    private var isDataShow = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSocket = SocketManager.getInstance(requireContext())!!.getSocket()
        isDataShow = savedInstanceState?.getBoolean(IS_CONNECTING) ?: true


        initViewPage()
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_CONNECTING, false)
    }


    private fun initViewPage() {
        val viewPagerAdapter = ViewPagerAdapter(requireActivity())
        viewPagerAdapter.addFragment(ListUserFragment())
        mBinding.pagerHome.apply {
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    requireActivity().title = if (position == 0){
                        "All User"

                    } else{
                        "Group User"
                    }
                }
            })
        }

    }



}