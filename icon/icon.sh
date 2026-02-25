#!/bin/bash

IFS=$'\n'

CUR_DIR=$(pwd | grep -o '[^/]*$')

if [[ $CUR_DIR != "icon" ]]
then
  if [ -d icon ]
  then
    cd icon
  else
    echo "[Error] 올바른 스크립트 실행 경로가 아닙니다 프로젝트 루트나 icon 디렉토리에서 bash 로 실행시켜 주세요"
    exit 1
  fi
fi

MODULE_NAME="library/design"
MODULE_PATH="../$MODULE_NAME"

PATH_MDPI=$MODULE_PATH'/src/main/res/drawable'
PATH_HDPI=$MODULE_PATH'/src/main/res/drawable-hdpi'
PATH_XHDPI=$MODULE_PATH'/src/main/res/drawable-xhdpi'
PATH_XXHDPI=$MODULE_PATH'/src/main/res/drawable-xxhdpi'
PATH_XXXHDPI=$MODULE_PATH'/src/main/res/drawable-xxxhdpi'

[ ! -d "$PATH_MDPI" ] && mkdir -p "$PATH_MDPI" >/dev/null 2>&1
[ ! -d "$PATH_HDPI" ] && mkdir -p "$PATH_HDPI" >/dev/null 2>&1
[ ! -d "$PATH_XHDPI" ] && mkdir -p "$PATH_XHDPI" >/dev/null 2>&1
[ ! -d "$PATH_XXHDPI" ] && mkdir -p "$PATH_XXHDPI" >/dev/null 2>&1
[ ! -d "$PATH_XXXHDPI" ] && mkdir -p "$PATH_XXXHDPI" >/dev/null 2>&1

files=$(ls -rS *.png 2>/dev/null)

if [ -z "$files" ]; then
    echo "[Error] 처리할 .png 파일이 현재 디렉토리에 없습니다."
    exit 1
fi

count=0
for f in $files
do
    base_name="${f%.*}"
    clean_name=$(echo "$base_name" | sed -E 's/ \([0-9]+\)//g; s/\([0-9]+\)//g; s/-[0-9]+//g; s/(@[0-9](\.[0-9])?x|_(mdpi|hdpi|xhdpi|xxhdpi|xxxhdpi))//g')
    final_name=$(echo "$clean_name" | tr ' ' '_' | tr '[:upper:]' '[:lower:]' | sed 's/__/_/g').png
    rank=$((count % 5))
    case $rank in
        0) DEST_PATH="$PATH_MDPI/$final_name" ;;
        1) DEST_PATH="$PATH_HDPI/$final_name" ;;
        2) DEST_PATH="$PATH_XHDPI/$final_name" ;;
        3) DEST_PATH="$PATH_XXHDPI/$final_name" ;;
        4) DEST_PATH="$PATH_XXXHDPI/$final_name" ;;
    esac

    echo "$DEST_PATH"
    mv "$f" "$DEST_PATH"

    count=$((count + 1))
done

echo "------------------------------------------"
echo "✅ 완료! 총 $count 개의 파일이 이동되었습니다."