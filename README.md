# 🤖 Мобильное приложение «Трон»

Android-приложение — **AI-ассистент на основе GPT-5 Nano (OpenAI)**, реализованный с применением production-подхода к архитектуре, DI и сетевому взаимодействию.

Проект полностью реализован и представляет собой завершённую архитектурную систему с разделением слоёв, фоновыми сценариями и интеграцией AI-модели.

---

## 🧠 Суть приложения

«Трон» — голосовой AI-ассистент, который:

* работает как в фокусе приложения, так и вне его;
* записывает голос пользователя, пока активирована кнопка и приложение не закрыто;
* анализирует поток речи и фиксирует вопросы;
* преобразует вопрос в текстовую строку;
* отправляет запрос через OpenAI API (GPT-5 Nano);
* получает краткий ответ;
* доставляет результат через системные уведомления (`NotificationChannel`, `NotificationManager`).

Ассистент ориентирован на быстрые, лаконичные ответы и стабильную фоновую работу.

---

## ✨ Основные возможности

* Непрерывная запись речи при активированной кнопке
* Детекция вопросительных конструкций
* Формирование текстового запроса
* Интеграция с OpenAI API
* Отправка ответа через системное уведомление
* Работа в фоне
* Авторизация через Firebase Authentication
* Локальное хранение истории запросов (Room)

---

## 🏗 Архитектура

Проект реализован по принципам **Clean Architecture**:

* `presentation` — UI, ViewModel, управление состоянием
* `domain` — бизнес-логика, use-cases
* `data` — репозитории, API, база данных

Используется архитектурный паттерн **MVVM** с:

* ViewModel
* StateFlow / LiveData
* Lifecycle-aware компонентами

Dependency Injection реализован через **Dagger + Hilt**.

---

## 🌐 Сетевой слой (Production-структура)

* Retrofit
* OkHttp
* Moshi (основной JSON-конвертер)
* Coroutines
* Централизованная обработка ошибок
* Logging-interceptor (единая актуальная версия)

---

## 🧩 Технологический стек

### Core

* Kotlin
* Coroutines
* AndroidX Core KTX
* Lifecycle (runtime-ktx, viewmodel-ktx, livedata-ktx, runtime-compose)

### UI

* Jetpack Compose (BOM)
* Material 3
* Navigation Compose
* Activity Compose
* Fragment KTX / Fragment Compose
* RecyclerView

### Dependency Injection

* Dagger
* Hilt
* Hilt Navigation Compose

### Database

* Room (Runtime + KTX + KSP Compiler)

### Network

* Retrofit
* Moshi Kotlin
* OkHttp
* OkHttp Logging Interceptor

### Firebase

* Firebase BOM
* Firebase Authentication

### Testing

* JUnit
* AndroidX JUnit
* Espresso
* Compose UI Test

---

## 📦 Production Characteristics

* Чистое разделение слоёв
* Минимальная связность компонентов
* Централизованный DI-граф
* Предсказуемая работа фоновых сценариев
* Отсутствие дублирующих зависимостей
* Подготовленность к масштабированию

---

## 🎯 Назначение проекта

* Демонстрация production-уровня Android-архитектуры
* Интеграция AI-моделей в мобильное приложение
* Практика сложного DI-графа и сетевого слоя
* Работа с фоновыми процессами и уведомлениями

---

> Проект завершён и реализует полноценный AI-ассистент с архитектурной целостностью и масштабируемостью.
