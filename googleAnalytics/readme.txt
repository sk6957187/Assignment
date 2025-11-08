Read me file for add Google Analytics in own website

1. create account in:  https://analytics.google.com/
2. create html page.
3. add new account
    admin -> create -> account -> filled all details

4. go into Google Analytics and copy code
    admin -> data collection .. -> data stream -> enter in own properties and copy the measurement id inside header part.

    <script async src="https://www.googletagmanager.com/gtag/js?id=G-XXXXXXXX"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}

        gtag('config', 'G-XXXXXXXXX', {
            'page_title': 'Google Analytics Home',
            'page_path': '/google-analytics'
        });
    </script>

4. If you want to create event in particular button then add tag in

    readmeBtn.addEventListener("click", function () {

        gtag('event', 'readme_click', {
        'event_category': 'Button',
        'event_label': 'Readme File Button',
        'value': 1
          });

        // Wait 300ms to let GA send the event
        setTimeout(() => {
            window.location.href = "/google-analytics/readme";
        }, 300);
    });

