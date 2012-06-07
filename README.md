# Overview

This project implements Java bindings for [National University of Singapore](http://www.nus.edu.sg/)'s
[Integrated Virtual Learning Environment](http://ivle.nus.edu.sg/) Learning API, which is a JSON/XML endpoint.

---

# How to use?

## General

Functionality of this package is contained in the
Java package `com.nuscomputing.ivlelapi`.

Usage typically starts with the creation of a `IVLE` instance:

    String apiKey = "API key here";
    String username = "username here";
    String password = "password here";
    IVLE ivle = new IVLE(apiKey, username, password);

From here on, you can call any methods via the created instance:

    Module[] modules = ivle.getModules();
    for (Module module : modules) {
        ...
    }

## More information

For more information, please refer to the Javadoc.

---

# Licensing

com.nuscomputing.ivlelapi is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by the Free Software
Foundation, either version 3 of the License, or (at your option) any later version.

com.nuscomputing.ivlelapi is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with 
com.nuscomputing.ivlelapi. If not, see <http://www.gnu.org/licenses/>.

