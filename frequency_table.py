import csv
from collections import defaultdict

permutation_counts = defaultdict(lambda: {"yes": 0, "no": 0})

with open('./predictive_dataset.csv', mode='r') as file:
    reader = csv.reader(file)
    for row in reader:
        if len(row) != 5:
            continue
        permutation = tuple(row[:4])
        result = row[4].strip().lower()
        if result in ["yes", "no"]:
            permutation_counts[permutation][result] += 1

print(f"{'Permutation':<20} {'Yes':<5} {'No':<5}")
print("-" * 50)
for permutation, counts in permutation_counts.items():
    print(f"{','.join(permutation):<20} {counts['yes']:<5} {counts['no']:<5}")