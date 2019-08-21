# HTTPBackendListenerClient (JMeter)

## Content

- [Features](#features)
- [Dependencies](#dependencies)
- [Installation](#installation)
- [Configuration](#configuration)
- [Contribution](#contribution)

## Features

* Plug-n-Play, easy to use module
* Handle POST endpoints
* Supports GZip compression

## Dependencies

* GSON - 2.8.5 ([Link](https://mvnrepository.com/artifact/com.google.code.gson/gson "GSON"))

## Installation

* Copy the JAR file (BackendListener & GSON) in the JMeter folder under `/lib/ext`
* The Jar should now be visible under the drop down available when you add a BackendListener in your project.


## Configuration

Configuration | Default value     | Description
----------------- | -------- | -----------
Protocol | http | Protocol of the endpoint where data needs to be sent
hostName | 127.0.0.1 | Hostname of the endpoint where data needs to be sent
url | /senddata | URL of the endpoint where data needs to be sent
port | 80 | Port of the endpoint where data needs to be sent
interval | 30 (in seconds) | Interval duration after which the data needs to be in sent. Values in seconds.
compression | false | enable or disable GZip compression of the data being sent

## Contribution

If you encountered an Issue, please add a screenshot of the bug or a code snippet. 

Quickest way to solve issue is to reproduce it on the example project

Pull requests are welcome. If you want to change the API or imporove something, feel free to create an issue and discuss it first.

---

**MIT Licensed**
