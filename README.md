# Quarrying

Quarrying lets your pickaxes and shovels mine a whole 3x3 area at once instead of just one block.

## Features

- `Quarrying` enchantment (levels I–III) for pickaxes and shovels
- 3x3 area mining, depth depends on the level
- Strict mode so you don't accidentally break stuff you don't want to
- Should be compatible with logging plugins (I hope lol)

## How it works

### Mining depth

The level of `Quarrying` on your tool decides how deep the 3x3 area goes:

- **I** → 3x3x1
- **II** → 3x3x2
- **III** → 3x3x3

Hold your tool and press your **offhand swap key** to cycle through the different depths. You can turn it off entirely too if you just want to mine normally.

### Filter mode

By default the tool grabs anything in the same category (ore/stone, dirt/sand/etc). Press Shift + F for 'Filter mode' to only mine the exact block you started on

