dev:
	./gradlew run

clean:
	./gradlew clean

# проверка готовит приложение к тестам
setup: build-css
	./gradlew installDist

# образ на render собирается этой целью
build: setup

build-css:
	npm ci
	npm run build:css

start:
	npx --no concurrently --kill-others --names css,app \
		"npm run watch:css" \
		"APP_ENV=development ./gradlew run"

.PHONY: build