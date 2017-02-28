package com.controller;

import com.model.Model;
import java.io.File;
import java.io.IOException;

public class DataSave implements Data{
    public void upload (Model model, File file) throws IOException, ClassNotFoundException {
        model.read (file);
    }

    public void unload (Model model, File file) throws IOException {
        model.write (file);
    }
}
