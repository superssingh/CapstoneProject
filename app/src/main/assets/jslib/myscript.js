google.books.load();

        function alertNotFound() {
          alert("could not embed the book!");
        }

        function initialize(val) {
            var viewer = new google.books.DefaultViewer(document.getElementById('viewerCanvas'));
            viewer.load(val, alertNotFound);
        }

google.books.setOnLoadCallback(initialize);