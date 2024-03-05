/*
 *
 * (c) Copyright Ascensio System Limited 2010-2018
 *
 * The MIT License (MIT)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
*/


package com.dooleen.service.file.manage.fastdfs.entity;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.dooleen.service.file.manage.fastdfs.helpers.DocumentManager;
import com.dooleen.service.file.manage.fastdfs.helpers.FileUtility;

public class FileModel
{
    public String type = "desktop";
    public String documentType;
    public Document document;
    public EditorConfig editorConfig;
    public String token;
    
    public FileModel(String fileName,String tenantId)
    {
        if (fileName == null) fileName = "";
        fileName = fileName.trim();
        documentType = FileUtility.GetFileType(fileName,tenantId).toString().toLowerCase();

        document = new Document();
        document.title = fileName;
        document.url = DocumentManager.GetFileUri(fileName);
        document.fileType = FileUtility.GetFileExtension(fileName,tenantId).replace(".", "");
    }

    public void InitDesktop()
    {
        type = "embedded";
        editorConfig.InitDesktop(document.url);
    }

    public void BuildToken()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("documentType", documentType);
        map.put("document", document);
        map.put("editorConfig", editorConfig);

        token = DocumentManager.CreateToken(map);
    }

    public class Document
    {
        public String title;
        public String url;
        public String fileType;
        public String key;
    }

    public class EditorConfig
    {
        public String mode = "edit";
        public String callbackUrl;
        public User user;
        public Customization customization;
        public Embedded embedded;

        public EditorConfig()
        {
            user = new User();
            customization = new Customization();
        }

        public void InitDesktop(String url)
        {
            embedded = new Embedded();
            embedded.saveUrl = url;
            embedded.embedUrl = url;
            embedded.shareUrl = url;
            embedded.toolbarDocked = "top";
        }

        public class User
        {
            public String id;
            public String name = "John Smith";
        }

        public class Customization
        {
            public Goback goback;

            public Customization()
            {
                goback = new Goback();
            }

            public class Goback
            {
                public String url;
            }
        }

        public class Embedded
        {
            public String saveUrl;
            public String embedUrl;
            public String shareUrl;
            public String toolbarDocked;
        }
    }


    public static String Serialize(FileModel model)
    {
        Gson gson = new Gson();
        return gson.toJson(model);
    }
}
