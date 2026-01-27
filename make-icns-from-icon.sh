#!/bin/bash

# Скрипт для создания .icns файла из изображения
# Использование: ./create-icon.sh <путь_к_изображению>

if [ $# -eq 0 ]; then
    echo "Использование: $0 <путь_к_изображению>"
    echo "Пример: $0 icon.png"
    exit 1
fi

INPUT_IMAGE="$1"
ICONSET_DIR="icon.iconset"
OUTPUT_ICNS="icon.icns"

if [ ! -f "$INPUT_IMAGE" ]; then
    echo "Ошибка: файл '$INPUT_IMAGE' не найден"
    exit 1
fi

# Удаляем старый iconset, если существует
rm -rf "$ICONSET_DIR"

# Создаем iconset директорию
mkdir "$ICONSET_DIR"

# Создаем иконки разных размеров
echo "Создание иконок разных размеров..."

sips -z 16 16     "$INPUT_IMAGE" --out "$ICONSET_DIR/icon_16x16.png"
sips -z 32 32     "$INPUT_IMAGE" --out "$ICONSET_DIR/icon_16x16@2x.png"
sips -z 32 32     "$INPUT_IMAGE" --out "$ICONSET_DIR/icon_32x32.png"
sips -z 64 64     "$INPUT_IMAGE" --out "$ICONSET_DIR/icon_32x32@2x.png"
sips -z 128 128   "$INPUT_IMAGE" --out "$ICONSET_DIR/icon_128x128.png"
sips -z 256 256   "$INPUT_IMAGE" --out "$ICONSET_DIR/icon_128x128@2x.png"
sips -z 256 256   "$INPUT_IMAGE" --out "$ICONSET_DIR/icon_256x256.png"
sips -z 512 512   "$INPUT_IMAGE" --out "$ICONSET_DIR/icon_256x256@2x.png"
sips -z 512 512   "$INPUT_IMAGE" --out "$ICONSET_DIR/icon_512x512.png"
sips -z 1024 1024 "$INPUT_IMAGE" --out "$ICONSET_DIR/icon_512x512@2x.png"

# Конвертируем iconset в .icns
echo "Конвертация в .icns..."
iconutil -c icns "$ICONSET_DIR" -o "$OUTPUT_ICNS"

# Удаляем временную директорию
rm -rf "$ICONSET_DIR"

if [ -f "$OUTPUT_ICNS" ]; then
    echo "✓ Иконка успешно создана: $OUTPUT_ICNS"
    echo "Теперь запустите ./create-mac-app.sh для создания приложения с новой иконкой"
else
    echo "Ошибка при создании иконки"
    exit 1
fi
