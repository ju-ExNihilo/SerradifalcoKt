package com.jgdeveloppement.pizza_serradifalco.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.databinding.FragmentMapBinding

class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private var googleMap: GoogleMap? = null
    private var mapFragment: SupportMapFragment? = null
    private var latLng: LatLng? = null
    private var markerTitle: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        latLng = LatLng(43.1395408, 5.9947193)
        mapFragment = childFragmentManager.findFragmentById(R.id.g_map_view) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        binding.serradifalcoButton.setOnClickListener{
            latLng = LatLng(43.1395408, 5.9947193)
            markerTitle = getString(R.string.serradifalco)
            mapFragment?.getMapAsync(this)
        }

        binding.leonardoButton.setOnClickListener {
            latLng = LatLng(43.1361416, 5.9804128)
            markerTitle = getString(R.string.leonardo)
            mapFragment?.getMapAsync(this)
        }

    }

    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0
        googleMap?.clear()
        googleMap?.apply {
            addMarker(
                MarkerOptions()
                    .position(latLng!!)
                    .title(markerTitle)
            )
            moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16F))
        }
    }

}
