package com.anjo.starwarswikicompose.exception

import java.io.IOException


class NoConnectivityException(message: String?) : IOException(message) {
}