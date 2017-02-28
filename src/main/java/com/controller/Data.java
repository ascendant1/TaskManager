package com.controller;

import com.model.Model;
import java.io.File;
import java.io.IOException;

public interface Data {
    String FILE_DATA = "tasks.bin";

    void upload (Model model, File file) throws IOException, ClassNotFoundException ;

    void unload (Model model, File file) throws IOException;
}
