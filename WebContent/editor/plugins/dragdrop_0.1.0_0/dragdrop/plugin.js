CKEDITOR.plugins.add( 'dragdrop', {
    init: function( editor ) {
        backends = {
            imgur: {
                upload: uploadImgur,
                required: ['clientId'],
                init: function() {}
            },
            s3: {
                upload: uploadS3,
                required: [
                    'bucket', 'accessKeyId','secretAccessKey', 'region'
                ],
                init: function() {
                    var script = document.createElement('script');
                    script.async = 1;
                    script.src = 'https://sdk.amazonaws.com/js/aws-sdk-2.1.26.min.js';
                    document.body.appendChild(script);
                }
            }
        };

        var checkRequirement = function(condition, message) {
            if (!condition)
                throw Error("Assert failed" + (typeof message !== "undefined" ? ": " + message : ""));
        };

        function validateConfig() {
            var errorTemplate = 'DragDropUpload Error: ->';
            checkRequirement(
                editor.config.hasOwnProperty('dragdropConfig'),
                "Missing required dragdropConfig in CKEDITOR.config.js"
            );

            var backend = backends[editor.config.dragdropConfig.backend];

            var suppliedKeys = Object.keys(editor.config.dragdropConfig.settings);
            var requiredKeys = backend.required;

            var missing = requiredKeys.filter(function(key) {
                return suppliedKeys.indexOf(key) < 0
            });

            if (missing.length > 0) {
                throw 'Invalid Config: Missing required keys: ' + missing.join(', ')
            }
        }

       /* validateConfig();*/
/*
        var backend = backends[editor.config.dragdropConfig.backend];*/
      /*  backend.init();*/

        function doNothing(e) { }
        function orPopError(err) { alert(err.data.error) }

        function dropHandler(e) {
            e.preventDefault();
            var file = e.dataTransfer.files[0];
            console.log('parshant runs');
            uploadImgur(file);
         /*   backend.upload(file).then(insertImage, orPopError);*/
        }

        function insertImage(href) {
            var editor =CKEDITOR.instances['editor'];
            var elem = editor.document.createElement('img', {
                attributes: {
                    src: href
                }
            });
            editor.insertElement(elem);
        }

        function uploadImgur(file) {
        	  $("#loading1").show("slow");
        	var formData = new FormData();  
            formData.append("upload", file);
            var client = new XMLHttpRequest();
            client.open("post", "../uploadBlogDropFile", true);
            client.send(formData);
            
            client.onreadystatechange=function()
            {
            if (client.readyState==4 && client.status==200)
              {
            	var data=client.response;
            	insertImage(data);
            	  $("#loading1").toggle();
            	
              }
            }
        }

        function uploadS3(file) {
            var settings = editor.config.dragdropConfig.settings;
            AWS.config.update({accessKeyId: settings.accessKeyId, secretAccessKey: settings.secretAccessKey});
            AWS.config.region = 'us-east-1';

            console.log(settings);
            console.log(AWS.config);

            var bucket = new AWS.S3({params: {Bucket: settings.bucket}});
            var params = {Key: file.name, ContentType: file.type, Body: file, ACL: "public-read"};
            return new Promise(function(resolve, reject) {
                bucket.upload(params, function (err, data) {
                    if (!err) resolve(data.Location);
                    else reject(err);
                });
            });
        };

        CKEDITOR.on('instanceReady', function() {
            var iframeBase = document.querySelector('iframe').contentDocument.querySelector('html');
            var iframeBody = iframeBase.querySelector('body');

            iframeBody.ondragover = doNothing;
            iframeBody.ondrop = dropHandler;

        });
    }
});
