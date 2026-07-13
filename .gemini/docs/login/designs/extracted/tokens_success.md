# Token Inventory: stitch_success.html

## Tailwind Config Overrides

_(none found)_

## Global Styles

Inline `<style>` rules that apply globally — these affect every matching element regardless of class list.

```css
.material-symbols-outlined {
            font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24;
        }
        body {
            font-family: 'Manrope', sans-serif;
            background-color: #FFFBF0; /* Specific prompt request */
        }
        .animate-pulse-subtle {
            animation: pulse-subtle 3s cubic-bezier(0.4, 0, 0.6, 1) infinite;
        }
        @keyframes pulse-subtle {
            0%, 100% { opacity: 1; transform: scale(1); }
            50% { opacity: 0.85; transform: scale(1.02); }
        }
body {
      min-height: max(884px, 100dvh);
    }
```

## Motion Inventory

Captured animation vocabulary. Bucket each token via the Web-Motion Policy in `.claude/skills/_shared/motion.md`: **KEEP** the 4 non-interaction families (Ambient bg, Loading/Attention loop, Entrance, Value-driven) + honor reduced-motion; **DROP** all touch press (`active:*`, ripple) and pointer/hover (`hover:*`, `group-hover:*`) feedback. Per-element `animate-*` / `transition-*` / `active:` / `hover:` tags are annotated inline in the Elements section below.

### @keyframes (<style> blocks)

- pulse-subtle

### Keyframe magnitudes

Animated value ranges (the delta each animation moves through). Pin these in the blueprint's `## Motion` **Magnitude** column — they are the only source for scale/translate/opacity/offset amounts (duration/easing come from the shorthand above; the implementer must not invent magnitudes).

- **pulse-subtle**: scale 1→1.02; opacity 1→0.85

### JS animation drivers

- timed step (Value/Entrance)

## Elements

Each class is followed by its deterministic token interpretation when one applies (e.g. `mt-4 → margin-top: 16dp`). Classes with no annotation are layout primitives, state variants, or unrecognised — interpret them yourself.

**Element formats:**
- **Visual elements** (any class converts to a visual token, or has inline style) get a full block with one line per class.
- **Layout-only elements** (only structural classes like `flex`, `items-center`, `justify-between`) get a single compact line — they still appear in order so structural mismatches (Row vs Column, arrangement, alignment) remain visible.
- **Classless text children** (e.g. `<span>Label</span>` inside a button) also appear as a one-liner with their text, so sibling DOM order inside a flex container is preserved — compare it against the Compose content lambda order.

- [1] `<html>` `light`
### <!-- Google Fonts: Manrope -->

**[2] `<body>`**

1. `min-h-screen` → min-height: 100vh/vw
2. `flex`
3. `flex-col`
4. `text-on-surface`

### <!-- TopAppBar -->

**[3] `<header>`**

1. `w-full` → width: 100%
2. `sticky` (positioning: sticky — Compose: Box overlay or BottomBar slot)
3. `top-0` → top: 0dp
4. `bg-[#FFFDF6]` → background: #FFFDF6
5. `shadow-sm` → shadow: ~1dp elevation
6. `z-50` (z-index — Compose has no z-index; layering is order-based)
7. `flex`
8. `items-center`
9. `px-4` → horizontal padding: 16dp
10. `h-[56px]` → height: 56px

**[4] `<h1>` — "Device Setup"**

1. `text-[#6B5000]` → color: #6B5000
2. `font-headline`
3. `text-[22px]` → font-size: 22sp
4. `font-bold` → font-weight: 700 (Bold)

**[5] `<div>`**

1. `ml-auto` → left margin: auto

**[6] `<button>` — "settings"**

1. `material-symbols-outlined`
2. `text-on-surface-variant`
3. `hover:bg-surface-container-high` (motion: web-only — DROP — pointer/hover)
4. `transition-colors` (motion: transition hint — Entrance/Value family)
5. `p-2` → padding: 8dp
6. `rounded-full` → corner-radius: CircleShape
7. `active:scale-95` (motion: interaction — DROP — touch press feedback)
8. `transition-transform` (motion: transition hint — Entrance/Value family)
9. `duration-150` (motion: transition hint — Entrance/Value family)

### <!-- Main Content Canvas -->

**[7] `<main>`**

1. `flex-grow`
2. `flex`
3. `flex-col`
4. `items-center`
5. `px-4` → horizontal padding: 16dp
6. `py-6` → vertical padding: 24dp
7. `max-w-md`
8. `mx-auto` → horizontal margin: auto
9. `w-full` → width: 100%
10. `space-y-6` → children spaced 24dp vertically

### <!-- Aesthetic Illustration (Signal/Shield) -->

**[8] `<div>`**

1. `relative`
2. `w-48` → width: 192dp
3. `h-48` → height: 192dp
4. `flex`
5. `items-center`
6. `justify-center`
7. `mt-4` → top margin: 16dp

### <!-- Background Decoration -->

**[9] `<div>`**

1. `absolute` (positioning: absolute — Compose: Box overlay or BottomBar slot)
2. `inset-0` → inset: 0dp
3. `bg-secondary-container`
4. `opacity-20` → opacity: 0.2
5. `rounded-full` → corner-radius: CircleShape
6. `blur-3xl`
7. `animate-pulse-subtle` (motion: looping 'pulse-subtle' — looping (family: infer from keyframes))

**[10] `<div>`**

1. `relative`
2. `z-10` (z-index — Compose has no z-index; layering is order-based)
3. `bg-white` → background: white
4. `p-8` → padding: 32dp
5. `rounded-full` → corner-radius: CircleShape
6. `shadow-lg` → shadow: ~8dp elevation
7. `flex`
8. `items-center`
9. `justify-center`
10. `border-4` → border-width: 4dp
11. `border-primary-fixed`

**[11] `<span>` — "shield_with_heart"**

1. `material-symbols-outlined`
2. `text-primary`
3. `text-7xl` → font-size: 72sp
- _inline style_: `font-variation-settings: 'FILL' 1;`

**[12] `<div>`**

1. `absolute` (positioning: absolute — Compose: Box overlay or BottomBar slot)
2. `-top-2` → top: -8dp
3. `-right-2` → right: -8dp
4. `bg-primary`
5. `rounded-full` → corner-radius: CircleShape
6. `p-2` → padding: 8dp
7. `border-4` → border-width: 4dp
8. `border-[#FFFBF0]` → border-width: #FFFBF0

**[13] `<span>` — "bluetooth_searching"**

1. `material-symbols-outlined`
2. `text-on-primary`
3. `text-2xl` → font-size: 24sp

### <!-- Main Card -->

**[14] `<section>`**

1. `w-full` → width: 100%
2. `bg-[#FFFDF6]` → background: #FFFDF6
3. `rounded-md` → corner-radius: 6dp
4. `border` → border-width: 1dp
5. `border-[#CDC1A0]` → border-width: #CDC1A0
6. `p-6` → padding: 24dp
7. `shadow-sm` → shadow: ~1dp elevation
8. `space-y-5` → children spaced 20dp vertically

**[15] `<div>`**

1. `space-y-1` → children spaced 4dp vertically

**[16] `<h2>` — "Enter 6-Digit Device ID"**

1. `text-on-surface`
2. `font-headline`
3. `text-[18px]` → font-size: 18sp
4. `font-bold` → font-weight: 700 (Bold)
5. `leading-tight` → line-height: 1.25× font-size

**[17] `<p>` — "This ID is used as the unique name for Bluetooth Low Energy ..."**

1. `text-[#4E4730]` → color: #4E4730
2. `text-[14px]` → font-size: 14sp
3. `font-body`
4. `leading-normal` → line-height: 1.5× font-size

**[18] `<div>`**

1. `space-y-4` → children spaced 16dp vertically

### <!-- Outlined TextField -->

- [19] `<div>` `relative`
**[20] `<input>`**

1. `w-full` → width: 100%
2. `h-14` → height: 56dp
3. `px-4` → horizontal padding: 16dp
4. `bg-transparent` → background: transparent
5. `border-2` → border-width: 2dp
6. `border-outline-variant`
7. `rounded-lg` → corner-radius: 8dp
8. `focus:border-primary` (motion: web-only — DROP — pointer/hover)
9. `focus:ring-0` (motion: web-only — DROP — pointer/hover)
10. `text-lg` → font-size: 18sp
11. `font-mono`
12. `tracking-widest` → letter-spacing: 0.1em (× font-size for sp)
13. `text-on-surface`
14. `placeholder:text-on-surface-variant/50`
15. `transition-all` (motion: transition hint — Entrance/Value family)
16. `outline-none`

**[21] `<label>` — "Device ID"**

1. `absolute` (positioning: absolute — Compose: Box overlay or BottomBar slot)
2. `-top-2.5` → top: -10dp
3. `left-3` → left: 12dp
4. `px-1` → horizontal padding: 4dp
5. `bg-[#FFFDF6]` → background: #FFFDF6
6. `text-xs` → font-size: 12sp
7. `font-semibold` → font-weight: 600 (SemiBold)
8. `text-outline`

### <!-- Secondary Button -->

**[22] `<button>`**

1. `w-full` → width: 100%
2. `py-3` → vertical padding: 12dp
3. `px-4` → horizontal padding: 16dp
4. `rounded-lg` → corner-radius: 8dp
5. `text-[#6B5000]` → color: #6B5000
6. `font-bold` → font-weight: 700 (Bold)
7. `text-sm` → font-size: 14sp
8. `hover:bg-surface-variant/30` (motion: web-only — DROP — pointer/hover)
9. `active:scale-[0.98]` (motion: interaction — DROP — touch press feedback)
10. `transition-all` (motion: transition hint — Entrance/Value family)
11. `flex`
12. `items-center`
13. `justify-center`
14. `gap-2` → gap: 8dp
15. `border` → border-width: 1dp
16. `border-transparent` → border-color: transparent

**[23] `<span>` — "refresh Generate Random ID"**

1. `material-symbols-outlined`
2. `text-[20px]` → font-size: 20sp

### <!-- Primary Action -->

**[24] `<div>`**

1. `w-full` → width: 100%
2. `space-y-4` → children spaced 16dp vertically
3. `pt-4` → top padding: 16dp

**[25] `<button>` — "Start Advertising & Continue"**

1. `w-full` → width: 100%
2. `h-14` → height: 56dp
3. `bg-[#6B5000]` → background: #6B5000
4. `text-[#FFFFFF]` → color: #FFFFFF
5. `rounded-full` → corner-radius: CircleShape
6. `font-bold` → font-weight: 700 (Bold)
7. `text-base` → font-size: 16sp
8. `shadow-md` → shadow: ~4dp elevation
9. `hover:bg-primary/90` (motion: web-only — DROP — pointer/hover)
10. `active:scale-[0.97]` (motion: interaction — DROP — touch press feedback)
11. `transition-all` (motion: transition hint — Entrance/Value family)
12. `flex`
13. `items-center`
14. `justify-center`
15. `gap-3` → gap: 12dp

- [26] `<span>` `material-symbols-outlined` — "arrow_forward"
**[27] `<p>` — "Make sure Bluetooth permissions are granted."**

1. `text-[#4E4730]` → color: #4E4730
2. `text-[12px]` → font-size: 12sp
3. `text-center`
4. `font-body`

### <!-- Extra Contextual Info (Safety/Zero Harm Focus) -->

**[28] `<div>`**

1. `w-full` → width: 100%
2. `bg-surface-container-low`
3. `rounded-xl` → corner-radius: 12dp
4. `p-4` → padding: 16dp
5. `mt-8` → top margin: 32dp
6. `flex`
7. `gap-4` → gap: 16dp
8. `items-start`

**[29] `<span>` — "security"**

1. `material-symbols-outlined`
2. `text-primary`
3. `p-2` → padding: 8dp
4. `bg-white` → background: white
5. `rounded-lg` → corner-radius: 8dp
6. `shadow-sm` → shadow: ~1dp elevation

- [30] `<div>` `flex-1`
**[31] `<p>` — "Safety First Architecture"**

1. `text-xs` → font-size: 12sp
2. `font-bold` → font-weight: 700 (Bold)
3. `text-on-surface`
4. `mb-1` → bottom margin: 4dp

**[32] `<p>` — "UGZeroHarm encrypts local broadcast metadata to ensure your ..."**

1. `text-[11px]` → font-size: 11sp
2. `text-on-surface-variant`
3. `leading-snug` → line-height: 1.375× font-size

---
**Total elements**: 32 | **Visual**: 28 | **Layout-only**: 4 | **Total classes**: 185 | **Auto-converted**: 106 (57%)

