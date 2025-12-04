#!/usr/bin/env bash
if [[ $# -ne 2 ]]; then
  echo "Usage: $0 <year> <day>"
  exit 1
fi

YEAR="$1"
DAY="$2"

if [[ ! -f cookie.txt ]]; then
  echo "Error: cookie.txt file not found"
  exit 1
fi

COOKIE=$(< cookie.txt)

if [[ -z "$COOKIE" ]]; then
  echo "Error: cookie.txt is empty"
  exit 1
fi

URL="https://adventofcode.com/${YEAR}/day/${DAY}/input"
RESULT=$(
  curl --fail \
    -H "Cookie: ${COOKIE}" \
    -H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36 Edg/142.0.0.0" \
    "$URL"
)

if [[ $? -ne 0 ]]; then
  echo "Request failed"
  exit 1
fi

# Write file, return 1 if write fails

OUTDIR="inputs/${YEAR}"
mkdir -p "$OUTDIR"

OUTFILE="${OUTDIR}/day${DAY}.txt"
if ! printf "%s" "$RESULT" > "$OUTFILE"; then
  echo "Failed to write output file"
  exit 1
fi

exit 0