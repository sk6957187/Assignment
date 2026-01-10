// src/GoogleAnalytics.js

export const initGA = (measurementId) => {
  if (!measurementId) {
    console.warn("GA Measurement ID missing");
    return;
  }

  // Load GA script dynamically
  const script1 = document.createElement("script");
  script1.async = true;
  script1.src = `https://www.googletagmanager.com/gtag/js?id=${measurementId}`;
  document.head.appendChild(script1);

  // Initialize gtag
  const script2 = document.createElement("script");
  script2.innerHTML = `
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());
    gtag('config', '${measurementId}');
  `;
  document.head.appendChild(script2);
};

// Send a manual pageview using gtag
export const sendPageView = (page, title) => {
  if (typeof window.gtag !== "function") return;

  window.gtag("config", process.env.REACT_APP_GA_MEASUREMENT_ID, {
    page_path: page,
    page_title: title
  });
};
