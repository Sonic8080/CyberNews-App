# CyberNews & AI Insights Desktop Application

A modern, streamlined desktop application designed for cybersecurity professionals, tech enthusiasts, and researchers to track real-time cyber security updates and artificial intelligence breakthroughs. Built with agility and core performance in mind, this app aggregates feeds, manages offline bookmarks, and integrates an AI-driven contextual assistant.

---

## 🌟 Key Features

### 📰 Curated Tech Feeds
* **Targeted Sources:** Dynamic integration of industry-leading feeds including **BleepingComputer** for breaking cybersecurity infrastructure alerts and **Hugging Face Blog** for cutting-edge AI/LLM research papers and updates.
* **Clean Data Visualization:** Clean and structured GUI rendering. Articles with complex code blocks (`<code>`, `<pre>`), images, and embedded media elements are cleanly formatted without breaking layout constraints.
* **Optimal Readability:** Automated content truncation—displays the first 50% of the long article text to offer a fast, digestible overview.

### 🤖 CyberNews AI Assistant (Gemini Powered)
* **Contextual "Ask AI":** Fully operational analytical button embedded next to every article's detail view. Clicking it automatically pipes the article data as live context into the AI assistant.
* **Interactive Chat Interface:** A dedicated, custom-branded **CyberNews Assistant** modal that triggers seamlessly into the center of the screen, optimized for parsing malware reports, CVEs, and AI trends.
* **Flexible API Management:** Built-in secure API key configuration within the Settings panel allowing users to seamlessly input, view, save, or revoke/delete their personal Gemini API Key.

### ⚙️ Core Architecture & UI/UX Enhancements
* **Zero-Clutter Launch:** Eliminated initial language selection screens; the application boots directly into the primary news dashboard with translation triggers moved contextually into specific article panes.
* **Dynamic Cache Management:** Solved repetitive startup data caching issues. Cache is dynamically validated and flushed upon launch to ensure fresh data streams.
* **Reliable Notifications:** Integrated native background listener for immediate desktop push notifications whenever critical threat intelligence or new articles pull through the RSS feeds.
* **Local Storage (Bookmarks):** Fully functional "Save" mechanism that allows users to archive important threat models or research locally, accessible via a dedicated 3rd navigation menu tab.

---

## 🚀 Getting Started

### Prerequisites
* Native System Permissions (the app will explicitly request notification and local storage access upon initial startup setup).
* A valid Gemini API Key (configured via the in-app Settings panel to unlock the CyberNews Assistant features).