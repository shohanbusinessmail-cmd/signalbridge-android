package com.signalbridge.app.domain.model

/** A permission or capability the app needs, with its current grant state. */
data class PermissionRequirement(
    val permission: String,
    val rationaleKey: String,
    val rationaleDetailKey: String?,
    val isGranted: Boolean,
    val shouldShowRationale: Boolean,
    val canOpenSettings: Boolean,
    val isCarrierControlled: Boolean = false,
    val carrierControlledNoteKey: String? = null
)
